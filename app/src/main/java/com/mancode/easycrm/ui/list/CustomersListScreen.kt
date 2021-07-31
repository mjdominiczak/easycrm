package com.mancode.easycrm.ui.list

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomersList(viewModel: CustomersListViewModel, navController: NavController) {
    val customers by viewModel.customers.collectAsState(initial = listOf())
    val filterText = viewModel.filterState.value.text
    val sortOrder by viewModel.sortOrder
    val customersFiltered = if (filterText.isBlank())
        customers else
        customers.filter { it.raw.name.lowercase().contains(filterText.lowercase()) }
    val listToDisplay = when (sortOrder) {
        SortOrder.BY_NAME -> customersFiltered.sortedBy { it.raw.name }
        SortOrder.BY_NEXT_CONTACT_DATE -> customersFiltered.sortedBy { it.raw.dateNextContact }
    }
    if (customers.isNotEmpty()) {
        Crossfade(targetState = listToDisplay) {
            LazyColumn {
                items(items = it,
                    itemContent = { customer ->
                        val action =
                            CustomersListFragmentDirections.actionCustomersListFragmentToCustomerDetailFragment(
                                customer.raw.id
                            )
                        CustomerCard(
                            customer = customer,
                            onClick = { navController.navigate(action) })
                    })
            }
        }    } else {
        Button(onClick = { viewModel.populateDB() }, modifier = Modifier.padding(8.dp)) {
            Text(text = "Dodaj wpisy")
        }
    }
}
