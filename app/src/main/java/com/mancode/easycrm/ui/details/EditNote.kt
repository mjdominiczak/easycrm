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
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter

@Composable
fun NoteEditor(viewModel: CustomerDetailViewModel, navController: NavController) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        var clicked by remember { mutableStateOf(false) }
        var edited by remember { mutableStateOf(false) }
        val noteToUpdate by viewModel.getNoteToUpdate().collectAsState(initial = null)
        val time = if (viewModel.noteId == 0) {
            LocalDateTime.now()
        } else {
            (noteToUpdate?.timestamp ?: Instant.now()).atZone(ZoneId.systemDefault())
                .toLocalDateTime()
        }
        Text(text = time?.format(DateTimeFormatter.ofPattern("dd.MM.yyyy, hh:mm")) ?: "failed")
        Spacer(modifier = Modifier.height(8.dp))

        var modifiedNote by remember {
            mutableStateOf("")
        }
        val initialNote = noteToUpdate?.text.also {
            if (!edited) {
                modifiedNote = it ?: ""
            }
        } ?: ""
        val note = if (!edited) initialNote else modifiedNote
        TextField(
            value = note,
            onValueChange = {
                modifiedNote = it
                edited = true
            },
            label = { Text("Notatka") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = {
                if (note.isNotBlank()) {
                    if (viewModel.noteId == 0) {
                        viewModel.insertNote(time!!, note)
                    } else {
                        viewModel.updateNote(noteToUpdate!!.copy(text = note))
                    }
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