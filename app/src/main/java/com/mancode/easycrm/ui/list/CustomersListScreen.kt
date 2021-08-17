package com.mancode.easycrm.ui.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.mancode.easycrm.app.EasyCrmScreen
import org.threeten.bp.Instant

@Composable
fun CustomersList(viewModel: CustomersListViewModel, navController: NavController) {
    val customers by viewModel.customers.collectAsState(initial = listOf())
    val filterText = viewModel.filterState.value.text
    val sortOrder by viewModel.sortOrder
    val customersFiltered = if (filterText.isBlank())
        customers else
        customers.filter { it.raw.name.lowercase().contains(filterText.lowercase()) }
    val listToDisplay = when (sortOrder) {
        SortOrder.BY_NAME -> customersFiltered.sortedBy { it.raw.name.lowercase() }
        SortOrder.BY_NEXT_CONTACT_DATE -> customersFiltered.sortedBy {
            it.raw.dateNextContact ?: Instant.now().plusSeconds(3600 * 24 * 365 * 100L)
        }
    }
    if (customers.isNotEmpty()) {
        Crossfade(targetState = listToDisplay) {
            LazyColumn {
                items(items = it,
                    itemContent = { customer ->
                        CustomerCard(
                            customer = customer,
                            onClick = {
                                navController.navigate(
                                    "${EasyCrmScreen.CustomerDetail.name}/${customer.raw.id}"
                                )
                            })
                    })
            }
        }
    } else {
        Button(onClick = { viewModel.populateDB() }, modifier = Modifier.padding(8.dp)) {
            Text(text = "Dodaj wpisy")
        }
    }
}
