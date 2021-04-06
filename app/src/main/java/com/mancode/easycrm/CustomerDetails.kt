package com.mancode.easycrm

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.mancode.easycrm.data.Contact
import com.mancode.easycrm.data.Customer
import com.mancode.easycrm.data.customers
import com.mancode.easycrm.ui.theme.EasyCrmTheme
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

@Preview
@Composable
fun CustomerDetailsScreen(customer: Customer = customers[0]) {
    EasyCrmTheme {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = customer.name,
                    style = MaterialTheme.typography.h4,
                    modifier = Modifier.weight(0.6f)
                )
                StatusChip(
                    text = "Zbieranie danych",
                    modifier = Modifier.weight(0.4f)
                )
            }
            Text(
                text = "Ostatni kontakt: ${customer.dateLastContacted ?: "brak"}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Kolejny kontakt: ${customer.dateNextContact ?: "brak"}",
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                Text(
                    text = "Osoby kontaktowe:",
                    style = MaterialTheme.typography.h6
                )
                for (contact in customer.contacts) {
                    ContactRow(contact)
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Column {
                Text(
                    text = "Notatki:",
                    style = MaterialTheme.typography.h6
                )
                for (note in customer.notes) {
                    val dateTime = LocalDateTime.ofInstant(note.timeStamp, ZoneId.systemDefault())
                        .format(DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm"))
                    Text(text = "\n$dateTime\n${note.text}")
                }
            }
        }
    }
}

@Composable
private fun ContactRow(contact: Contact) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = "${contact.name}, tel: ${contact.phoneNumber}")
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (contact.email != null) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = "")
                }
            }
            if (contact.phoneNumber != null) {
                val context = LocalContext.current
                IconButton(onClick = { dialContact(context, contact.phoneNumber) }) {
                    Icon(imageVector = Icons.Filled.Call, contentDescription = "")
                }
            }
        }
    }
}

private fun dialContact(context: Context, phoneNumber: String) {
    val intent = Intent(Intent.ACTION_DIAL)
    intent.data = Uri.parse("tel:$phoneNumber")
    ContextCompat.startActivity(context, intent, null)
}