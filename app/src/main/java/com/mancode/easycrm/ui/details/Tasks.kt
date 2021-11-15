package com.mancode.easycrm.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mancode.easycrm.db.Task

@Composable
fun TaskRow(task: Task, onTaskCheckedChanged: (Task) -> Unit, onTaskDeleted: (Task) -> Unit) {
    Column(
        modifier = Modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .height(32.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = task.done, onCheckedChange = { onTaskCheckedChanged(task) })
            Spacer(modifier = Modifier.width(16.dp))
            val style =
                if (task.done) TextStyle(textDecoration = TextDecoration.LineThrough)
                else TextStyle.Default
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = task.description,
                    style = style
                )
                IconButton(onClick = { onTaskDeleted(task) }) {
                    Image(imageVector = Icons.Default.Delete, contentDescription = "", alpha = 0.5f)
                }
            }
        }
//        Row(modifier = Modifier.padding(start = 32.dp)) {
//            TextButton(onClick = { /*TODO*/ }) {
//                Text(text = "Przypomnienie")
//            }
//        }
    }
}

@Composable
fun AddTaskRow(onTaskAdded: (String) -> Unit) {
    var text by remember { mutableStateOf("") }
    OutlinedTextField(
        value = text,
        onValueChange = { text = it },
        placeholder = { Text(text = "Opis zadania") },
        leadingIcon = {
            Image(
                imageVector = Icons.Default.Add,
                contentDescription = "",
                alpha = 0.7f
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                onTaskAdded(text)
                text = ""
            }) {
                Icon(imageVector = Icons.Default.Done, contentDescription = "")
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun TaskList(
    tasks: List<Task>,
    onTaskCheckedChanged: (Task) -> Unit,
    onTaskDeleted: (Task) -> Unit
) {
    Column {
        tasks.forEach {
            TaskRow(
                task = it,
                onTaskCheckedChanged = { task -> onTaskCheckedChanged(task) },
                onTaskDeleted = { task -> onTaskDeleted(task) }
            )
        }
    }
}

@Preview
@Composable
fun TaskRowPreview() {
    TaskRow(
        task = Task(id = 1, customerId = 1, description = "Opis zadania", done = true),
        onTaskCheckedChanged = {},
        onTaskDeleted = {}
    )
}

@Preview
@Composable
fun TaskListPreview() {
    val tasks = listOf(
        Task(id = 1, customerId = 1, description = "Opis zadania 1", done = true),
        Task(id = 2, customerId = 1, description = "Opis zadania 2", done = false),
        Task(id = 3, customerId = 1, description = "Opis zadania 3", done = false),
        Task(id = 4, customerId = 1, description = "Opis zadania 4", done = true),
        Task(id = 5, customerId = 1, description = "Opis zadania 5", done = false),
    )
    TaskList(tasks = tasks, {}, {})
}

