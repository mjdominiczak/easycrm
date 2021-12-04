package com.mancode.easycrm.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import com.mancode.easycrm.db.Task
import com.mancode.easycrm.ui.views.ExpandableCard
import com.mancode.easycrm.ui.views.ScheduleButton
import com.mancode.easycrm.utils.showDatePicker
import org.threeten.bp.Instant

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskRow(
    task: Task,
    onTaskCheckedChanged: (Task) -> Unit,
    onTaskDateAdded: (Task, Long) -> Unit,
    onTaskDeleted: (Task) -> Unit
) {
    ExpandableCard(
        content = {
            val color = if (task.dueDate != null) {
                if (task.dueDate.isBefore(Instant.now())) {
                    Color.Red
                } else {
                    MaterialTheme.colors.secondaryVariant
                }
            } else {
                Color.LightGray
            }
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = color,
                shape = MaterialTheme.shapes.small,
            ) {
                Surface(
                    modifier = Modifier.padding(top = 4.dp),
                    shape = MaterialTheme.shapes.small.copy(
                        bottomStart = CornerSize(0.dp),
                        bottomEnd = CornerSize(0.dp)
                    ),
                    color = MaterialTheme.colors.surface
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(32.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = task.done,
                            onCheckedChange = { onTaskCheckedChanged(task) })
                        Spacer(modifier = Modifier.width(16.dp))
                        val style =
                            if (task.done) TextStyle(textDecoration = TextDecoration.LineThrough)
                            else TextStyle.Default
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = task.description,
                            style = style
                        )
                    }
                }
            }
        },
        additionalContent = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                val isExpanded = task.dueDate != null
                val fragmentManager =
                    (LocalContext.current as FragmentActivity).supportFragmentManager
                ScheduleButton(
                    onClick = {
                        showDatePicker(
                            fragmentManager = fragmentManager,
                            instant = task.dueDate,
                            onDateEntered = { stamp -> onTaskDateAdded(task, stamp) }
                        )
                    },
                    expanded = isExpanded,
                    instant = task.dueDate
                )
                IconButton(onClick = { onTaskDeleted(task) }) {
                    Image(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "",
                        alpha = 0.5f
                    )
                }
            }
        }
    )
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
    onTaskDateAdded: (Task, Long) -> Unit,
    onTaskDeleted: (Task) -> Unit
) {
    Column {
        tasks.forEach {
            TaskRow(
                task = it,
                onTaskCheckedChanged = { task -> onTaskCheckedChanged(task) },
                onTaskDateAdded = { task, stamp -> onTaskDateAdded(task, stamp) },
                onTaskDeleted = { task -> onTaskDeleted(task) }
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Preview
@Composable
fun TaskRowPreview() {
    TaskRow(
        task = Task(id = 1, customerId = 1, description = "Opis zadania", done = true),
        onTaskCheckedChanged = {},
        onTaskDateAdded = { _, _ -> },
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
    TaskList(tasks = tasks, {}, { _, _ -> }, {})
}

