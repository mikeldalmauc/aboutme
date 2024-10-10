package com.example.myapplication.ui.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.myapplication.R

@Composable
fun TodoApp(viewModel: TodoViewModel) {

    val todos: List<TodoItem> by viewModel.todoList.collectAsState()
    var newTodoTitle by remember { mutableStateOf(TextFieldValue("")) }

    val todosNotDone = todos.filterNot { it.completed }
    val todoDone = todos.filter { it.completed }
    var todoDoneExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp)
        , horizontalAlignment = Alignment.CenterHorizontally
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
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            items(
                items = todosNotDone.sortedBy { it.sortId },
                key = { it.id }
            ) { todo ->
                TodoItemView(
                    todo = todo,
                    alfa = 1.0f,
                    onCheckedChange = {
                        viewModel.updateTodoStatus(todo, it)
                    },
                    onDeleteClick = {
                        viewModel.deleteTodo(todo)
                    }
                )
            }

            item {
                HorizontalDivider()
            }
            if(todoDoneExpanded){
                // EXPAND BUTTON
                item {
                    ToggleExpandButton(
                        { todoDoneExpanded = todoDoneExpanded.not() },
                        R.drawable.baseline_keyboard_arrow_down_24
                    )
                }

                // DONES LIST
                items(
                    items = todoDone.sortedByDescending { it.fechaCompletado }
                    , key = { it.id }
                ) { todo ->
                    TodoItemView(
                        todo = todo,
                        alfa = 0.6f,
                        onCheckedChange = {
                            viewModel.updateTodoStatus(todo, it)
                        },
                        onDeleteClick = {
                            viewModel.deleteTodo(todo)
                        },
                    )
                }
            }else{
                // COLLAPSE BUTTON
                item {
                    ToggleExpandButton(
                        { todoDoneExpanded = todoDoneExpanded.not() },
                        R.drawable.baseline_keyboard_arrow_up_24
                    )
                }
            }

        }

    }
}


@Composable
fun ToggleExpandButton(todoDoneExpanded: () -> Unit, id : Int){
    IconButton(
        onClick = todoDoneExpanded
        , modifier = Modifier
            .padding(2.dp) // Padding del botón
    ) {
        Icon(
            painter = painterResource(id = id ),
            contentDescription = "Icono para explandir la lista de elementos completados",
            modifier = Modifier
                .size(24.dp)
                .alpha(0.6f)
            , // Tamaño del icono
            // Centrar el icono dentro del recuadro
        )
    }
}

@Composable
fun TodoItemView(todo: TodoItem, alfa: Float, onCheckedChange: (Boolean) -> Unit, onDeleteClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .alpha(alfa)
    ) {
        Checkbox(
            checked = todo.completed,
            onCheckedChange = onCheckedChange
        )
        Text(
            text = todo.title,
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp),
            style = if (todo.completed) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle.Default
        )
        IconButton(onClick = onDeleteClick) {
            Icon(Icons.Default.Delete, contentDescription = "Eliminar")
        }
    }
}