package com.example.myapplication.ui.componentes

import LoaderViewModel
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProgressScreen(viewModel: LoaderViewModel) {
    val progress : Float by viewModel.progress.observeAsState(initial = 0f) // Observar el progreso

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Barra de progreso circular
        CircularProgressIndicator(
            progress = { progress }
        )

        LinearProgressIndicator(
            progress = { progress }
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para iniciar el progreso
        Button(onClick = {
            viewModel.startProgress() // Iniciar el progreso cuando se presiona el botón
        }) {
            Text(text = "Iniciar Progreso")
        }
    }
}
@Composable
fun ProgressScreen2(viewModel: LoaderViewModel) {
    val progress : Float by viewModel.progress.observeAsState(initial = 0f) // Observar el progreso

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Barra de progreso circular
        CircularProgressIndicator(
            progress = { progress },
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            strokeWidth = 10.dp,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(13.dp))

        // Botón para iniciar el progreso
        Button(onClick = {
            viewModel.startProgress() // Iniciar el progreso cuando se presiona el botón
        }) {
            Text(text = "Iniciar Progreso")
        }
    }
}

@Composable
fun ProgressScreen3(viewModel: LoaderViewModel) {
    val progress : Float by viewModel.progress.observeAsState(initial = 0f) // Observar el progreso

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Barra de progreso circular
        CircularProgressIndicator(
            progress = { progress },
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            strokeWidth = 50.dp,
            trackColor = MaterialTheme.colorScheme.secondaryContainer,
            modifier = Modifier.size(100.dp)
        )
        Spacer(modifier = Modifier.height(13.dp))

        // Botón para iniciar el progreso
        Button(onClick = {
            viewModel.startProgress() // Iniciar el progreso cuando se presiona el botón
        }) {
            Text(text = "Iniciar Progreso")
        }
    }
}