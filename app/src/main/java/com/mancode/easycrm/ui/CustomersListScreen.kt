package com.mancode.easycrm.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.mancode.easycrm.data.customers

@Composable
fun CustomersList() {
    Column {
        for (customer in customers) {
            CustomerCard(customer = customer)
        }
    }
}
