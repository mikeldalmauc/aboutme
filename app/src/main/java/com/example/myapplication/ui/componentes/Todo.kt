package com.example.myapplication.ui.componentes

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AppTheme

@Composable
@Preview
fun TodoAppPreview(){
    AppTheme {

    TodoApp(TodoViewModel())
    }
}
@Composable
fun TodoApp(viewModel: TodoViewModel) {

    val todoDone: List<TodoItem> by viewModel.todoDone.collectAsState()
    val todosNotDone: List<TodoItem> by viewModel.todosNotDone.collectAsState()

    var newTodoTitle by remember { mutableStateOf(TextFieldValue("")) }
    var todoDoneExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally
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
                items = todosNotDone,
                key = { it.id }
            ) { todo ->
                SortableTodoItemView(
                    todo = todo,
                    alfa = 1.0f,
                    onCheckedChange = {
                        viewModel.updateTodoStatus(todo, it)
                    },
                    onDeleteClick = {
                        viewModel.deleteTodo(todo)
                    },
                    onFloatClick = {
                        viewModel.floatItem(todo)
                    },
                    onSinkClick = {
                        viewModel.sinkItem(todo)
                    }
                )
            }

            item {
                HorizontalDivider()
            }
            if (todoDoneExpanded) {

                // EXPAND BUTTON
                item {
                    ToggleExpandButton(
                        { todoDoneExpanded = todoDoneExpanded.not() },
                        R.drawable.baseline_keyboard_arrow_down_24
                    )
                }

                // DONES LIST
                items(
                    items = todoDone, key = { it.id }
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
            } else {

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
fun ToggleExpandButton(todoDoneExpanded: () -> Unit, id: Int) {
    IconButton(
        onClick = todoDoneExpanded, modifier = Modifier
            .padding(2.dp) // Padding del botón
    ) {
        Icon(
            painter = painterResource(id = id),
            contentDescription = "Icono para explandir la lista de elementos completados",
            modifier = Modifier
                .size(24.dp)
                .alpha(0.6f)
        )
    }
}

@Composable
fun SortableTodoItemView(
    todo: TodoItem,
    alfa: Float,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit,
    onFloatClick: () -> Unit,
    onSinkClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp)
            .alpha(alfa)
    ) {
        Column (
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(1.dp)
        )
        {
            Box(
                modifier = Modifier
                    .size(22.dp) // or your desired button size
                    .clickable(onClick = onFloatClick)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_up_24),
                    contentDescription = "Icono para hundir elementos",
                    modifier = Modifier
                        .size(22.dp)
                        .alpha(0.6f)
                        .align(Alignment.Center)
                )
            }
       /*     Box(
                modifier = Modifier
                    .size(27.dp) // or your desired button size
                    .clickable(onClick = onFloatClick)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    Icons.Default.Menu,
                    contentDescription = "Move",
                    modifier = Modifier
                        .size(27.dp)
                        .alpha(0.6f)
                        .align(Alignment.Center)
                )
            }*/
            Box(
                modifier = Modifier
                    .size(22.dp) // or your desired button size
                    .clickable(onClick = onSinkClick)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_keyboard_arrow_down_24),
                    contentDescription = "Icono flotar elementos",
                    modifier = Modifier
                        .size(22.dp)
                        .alpha(0.6f)
                        .align(Alignment.Center)
                )
            }
        }
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

@Composable
fun TodoItemView(
    todo: TodoItem,
    alfa: Float,
    onCheckedChange: (Boolean) -> Unit,
    onDeleteClick: () -> Unit
) {
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