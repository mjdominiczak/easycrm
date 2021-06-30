package com.mancode.easycrm.ui.details

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.mancode.easycrm.db.Contact
import com.mancode.easycrm.ui.list.StatusChip
import com.mancode.easycrm.utils.dialContact
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun CustomerDetailsScreen(viewModel: CustomerDetailViewModel, navController: NavController) {
    val customer by viewModel.customer.collectAsState(initial = null)
    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = customer?.raw?.name ?: "",
                style = MaterialTheme.typography.h4,
                modifier = Modifier.weight(0.6f)
            )
            StatusChip(
                text = "Zbieranie danych",
                modifier = Modifier.weight(0.4f)
            )
        }
        Text(
            text = "Ostatni kontakt: ${customer?.raw?.dateLastContacted ?: "brak"}",
            style = MaterialTheme.typography.body2
        )
        Text(
            text = "Kolejny kontakt: ${customer?.raw?.dateNextContact ?: "brak"}",
            style = MaterialTheme.typography.body2
        )
        Spacer(modifier = Modifier.height(24.dp))
        Column {
            Text(
                text = "Osoby kontaktowe:",
                style = MaterialTheme.typography.h6
            )
            for (contact in customer?.contacts ?: emptyList()) {
                ContactRow(contact)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "Notatki:",
            style = MaterialTheme.typography.h6
        )
        val notes by viewModel.getNotes().collectAsState(initial = emptyList())
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(notes) { note ->
                val dateTime = LocalDateTime.ofInstant(note.timestamp, ZoneId.systemDefault())
                    .format(DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm"))
                Text(
                    text = "\n$dateTime\n${note.text}",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val dirs =
                                CustomerDetailFragmentDirections.actionCustomerDetailFragmentToNoteDialog(
                                    viewModel.customerId!!,
                                    note.id
                                )
                            navController.navigate(dirs)
                        })
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ContactRow(contact: Contact) {
    val viewModel: CustomerDetailViewModel = viewModel()
    var showMenu by remember { mutableStateOf(false) }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(onLongClick = {
                showMenu = true
            }) {}
    ) {
        Text(text = contact.name)
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (contact.email != null) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Email, contentDescription = "")
                }
            }
            if (contact.hasPhoneNumber) {
                val context = LocalContext.current
                IconButton(onClick = { dialContact(context, contact) }) {
                    Icon(imageVector = Icons.Filled.Call, contentDescription = "")
                }
            }
        }
        DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
            DropdownMenuItem(onClick = { viewModel.deleteContact(contact) }) {
                Text(text = "Odłącz kontakt")
            }
        }
    }
}
