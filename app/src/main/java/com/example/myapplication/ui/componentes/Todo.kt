package com.example.myapplication.ui.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@Composable
fun TodoApp(viewModel: TodoViewModel) {

    val todoList: List<TodoItem> by viewModel.todoList.collectAsState()
    var newTodoTitle by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .padding(16.dp)
    ) {
        // Header para la lista de tareas

            Text(
                "TODOs",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 8.dp),
                fontFamily = MaterialTheme.typography.headlineLarge.fontFamily
            )
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider()
            Spacer(modifier = Modifier.height(16.dp))


        // Input para agregar una nueva tarea

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp) // Agregado un espacio al final para separar el input de la lista
            ) {
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


        // Lista de tareas
     /*
        items(
            items = todos.sortedBy { it.isCompleted }.sortedByDescending { it.fechaCompletado },
            key = { it.id }
        ) { todo ->
            TodoItemView(
                todo = todo,
                onCheckedChange = {
                    viewModel.updateTodoStatus(todo, it)
                },
                onDeleteClick = {
                    viewModel.deleteTodo(todo)
                }
            )
        }
        */
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