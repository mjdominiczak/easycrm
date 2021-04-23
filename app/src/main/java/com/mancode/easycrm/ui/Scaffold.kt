package com.mancode.easycrm

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable

@Composable
fun EasyCrmScaffold(content: @Composable (PaddingValues) -> Unit) {
    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = {
//                    Text(text = "Easy Robots CRM")
//                }
//            )
//        },
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "")
            }
        },
        content = content
    )
}