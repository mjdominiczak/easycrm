package com.mancode.easycrm.ui.list

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.mancode.easycrm.data.addresses
import com.mancode.easycrm.data.contacts
import com.mancode.easycrm.data.customers
import com.mancode.easycrm.db.Customer

@Composable
fun CustomersList(navController: NavController) {
    Column {
        for (customer in customers) {
            val action =
                CustomersListFragmentDirections.actionCustomersListFragmentToCustomerDetailFragment(
                    customer.raw.id
                )
            CustomerCard(customer = customer, onClick = { navController.navigate(action) })
        }
    }
}
