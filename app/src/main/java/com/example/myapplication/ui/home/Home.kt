package com.example.myapplication.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    // LazyColumn con fondo semi-transparente
    var homeViewModel = HomeViewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp)
        ) {
            item {
                Contador(homeViewModel)
            }
        }
    }
}

@Composable
fun Contador(viewModel: HomeViewModel) {
    // Obtener el valor del contador de LiveData
    val count: Int by viewModel.count.observeAsState(initial = 0)

    // Columna para organizar el contenido
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Texto que muestra el valor del contador
        Text(
            text = "Contador: $count",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el texto y los botones

        // Fila para organizar los botones de + y -
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botón de incrementar
            Button(onClick = { viewModel.upCount() }) {
                Text(text = "+")
            }

            // Botón de decrementar
            Button(onClick = { viewModel.downCount() }) {
                Text(text = "-")
            }
        }
    }
}
