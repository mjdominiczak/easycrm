package com.mancode.easycrm.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun NoteEditor(viewModel: CustomerDetailViewModel, navController: NavController) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        var clicked by remember { mutableStateOf(false) }
        val time = remember { LocalDateTime.now() }
        Text(text = time.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm")))
        Spacer(modifier = Modifier.height(8.dp))

        var note by remember { mutableStateOf("") }
        TextField(
            value = note,
            onValueChange = { note = it },
            label = { Text("Notatka") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (note.isNotBlank()) {
                    viewModel.insertNote(time, note)
                    navController.navigateUp()
                } else {
                    clicked = true
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Zapisz")
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (clicked && note.isBlank()) Text(text = "Nie możesz dodać pustej notatki!")
    }
}