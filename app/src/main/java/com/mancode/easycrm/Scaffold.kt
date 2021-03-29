package com.mancode.easycrm

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import com.mancode.easycrm.ui.theme.EasyCrmTheme

@Composable
fun EasyCrmScaffold(content: @Composable (PaddingValues) -> Unit) {
    EasyCrmTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Easy Robots CRM")
                    }
                )
            },
            floatingActionButton = {
                FloatingActionButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = "")
                }
            },
            content = content
        )
    }
}