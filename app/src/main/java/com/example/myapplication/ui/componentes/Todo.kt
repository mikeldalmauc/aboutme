package com.example.myapplication.ui.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun TodoApp(viewModel: TodoViewModel) {

    val todos by viewModel.todoList.collectAsState()

    var newTodoTitle by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Input para agregar una nueva tarea
        Row(modifier = Modifier.fillMaxWidth()) {
            BasicTextField(
                value = newTodoTitle,
                onValueChange = { newTodoTitle = it },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.small)
                    .padding(8.dp)
            )
            Button(
                onClick = {
                    if (newTodoTitle.text.isNotEmpty()) {
                        viewModel.addTodo(newTodoTitle.text)
                        newTodoTitle = TextFieldValue("")
                    }
                },
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Text("Agregar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Lista de tareas
        LazyColumn {
            items(todos) { todo ->
                TodoItemView(todo, onCheckedChange = {
                    viewModel.updateTodoStatus(todo, it)
                }, onDeleteClick = {
                    viewModel.deleteTodo(todo)
                })
            }
        }
    }
}

@Composable
fun TodoItemView(todo: TodoItem, onCheckedChange: (Boolean) -> Unit, onDeleteClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Checkbox(
            checked = todo.isCompleted,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = todo.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        )
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}