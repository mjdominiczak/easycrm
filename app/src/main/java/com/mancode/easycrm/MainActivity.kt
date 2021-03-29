package com.mancode.easycrm

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mancode.easycrm.ui.theme.EasyCrmTheme

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyCrmScaffold {
                MainContent()
            }
        }
    }
}

@Composable
fun ThemedContent(content: @Composable () -> Unit) {
    EasyCrmTheme {
        content()
    }
}

@Composable
fun MainContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {},
        ) {
            Text(text = "Easy Robots CRM")
        }
    }
}

@Preview
@Composable
fun DefaultPreview() {
    ThemedContent {
        MainContent()
    }
}