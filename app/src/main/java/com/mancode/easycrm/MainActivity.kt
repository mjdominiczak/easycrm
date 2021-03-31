package com.mancode.easycrm

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.mancode.easycrm.data.customers
import com.mancode.easycrm.ui.theme.EasyCrmTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CustomerDetailsScreen()
//            EasyCrmTheme {
//                EasyCrmScaffold {
//                    MainContent()
//                }
//            }
        }
    }
}

@Composable
fun MainContent() {
    Column {
        for (customer in customers) {
            CustomerCard(customer = customer)
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    EasyCrmTheme {
        MainContent()
    }
}