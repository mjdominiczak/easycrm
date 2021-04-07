package com.mancode.easycrm.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mancode.easycrm.data.Customer
import com.mancode.easycrm.ui.theme.DeepPurple300
import com.mancode.easycrm.ui.theme.Ebony

@Composable
fun CustomerCard(customer: Customer, onClick: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 8.dp)
            .fillMaxWidth()
            .clickable { onClick(customer.id) }
            .border(
                border = BorderStroke(1.dp, Ebony),
                shape = RoundedCornerShape(4.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = customer.name,
                    style = MaterialTheme.typography.h5,
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
            Spacer(modifier = Modifier.height(8.dp))
            CallButton(name = customer.contacts[0].name)
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
fun CallButton(name: String) {
    Button(onClick = { /*TODO*/ }) {
        Icon(imageVector = Icons.Filled.Call, contentDescription = "")
        Spacer(Modifier.width(8.dp))
        Text(text = name)
    }
}