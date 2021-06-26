package com.mancode.easycrm.ui.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mancode.easycrm.db.Contact
import com.mancode.easycrm.db.Customer
import com.mancode.easycrm.ui.theme.DeepPurple300
import com.mancode.easycrm.ui.theme.Ebony
import com.mancode.easycrm.utils.dialContact

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomerCard(customer: Customer, onClick: (Int) -> Unit) {
    val viewModel: CustomersListViewModel = viewModel()
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
            .fillMaxWidth()
            .border(
                border = BorderStroke(1.dp, Ebony),
                shape = RoundedCornerShape(4.dp)
            ),
        onClick = { onClick(customer.raw.id) }
    ) {
        Column(
            modifier = Modifier
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                var showMenu by remember { mutableStateOf(false) }
                Text(
                    text = customer.raw.name,
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.weight(0.6f)
                )
                StatusChip(
                    text = "Zbieranie danych",
                    modifier = Modifier.weight(0.4f)
                )
                IconButton(onClick = { showMenu = !showMenu }) {
                    Icon(Icons.Filled.MoreVert, "")
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(onClick = {
                            viewModel.deleteCustomer(customer)
                            showMenu = false
                        }) {
                            Text("Usuń klienta")
                        }
                    }
                }
            }
            Text(
                text = "Ostatni kontakt: ${customer.raw.dateLastContacted ?: "brak"}",
                style = MaterialTheme.typography.body2
            )
            Text(
                text = "Kolejny kontakt: ${customer.raw.dateNextContact ?: "brak"}",
                style = MaterialTheme.typography.body2
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (customer.contacts.isNotEmpty()) {
                val contact = customer.contacts[0]
                CallButton(contact)
            }
        }
    }
}

@Preview
@Composable
fun StatusChip(modifier: Modifier = Modifier, text: String = "testaasdas") {
    Surface(
        shape = RoundedCornerShape(20.dp),
        color = DeepPurple300,
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.body1,
            modifier = modifier
                .padding(8.dp)
        )
    }
}

@Composable
fun CallButton(contact: Contact) {
    val context = LocalContext.current
    Button(onClick = { dialContact(context, contact) }) {
        Icon(imageVector = Icons.Filled.Call, contentDescription = "")
        Spacer(Modifier.width(8.dp))
        Text(text = contact.name)
    }
}