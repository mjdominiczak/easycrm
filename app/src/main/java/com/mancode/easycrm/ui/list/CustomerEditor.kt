package com.mancode.easycrm.ui.list

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun CustomerEditor(viewModel: CustomersListViewModel, navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        var name by remember { mutableStateOf("") }
        TextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nazwa klienta") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (name.isNotEmpty()) {
                    viewModel.insertCustomer(name)
                    navController.navigateUp()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Zapisz")
        }
    }
}