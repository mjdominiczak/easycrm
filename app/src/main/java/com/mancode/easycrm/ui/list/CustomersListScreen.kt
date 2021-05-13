package com.mancode.easycrm.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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

@Composable
fun CustomersList(viewModel: CustomersListViewModel, navController: NavController) {
    val customers by viewModel.customers.collectAsState(initial = listOf())
    if (customers.isNotEmpty()) {
        LazyColumn {
            items(items = customers,
                itemContent = { customer ->
                    val action =
                        CustomersListFragmentDirections.actionCustomersListFragmentToCustomerDetailFragment(
                            customer.raw.id
                        )
                    CustomerCard(customer = customer, onClick = { navController.navigate(action) })
                })
        }
    } else {
        Button(onClick = { viewModel.populateDB() }, modifier = Modifier.padding(8.dp)) {
            Text(text = "Dodaj wpisy")
        }
    }
}
