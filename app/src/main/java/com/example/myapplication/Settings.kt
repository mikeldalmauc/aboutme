package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun SettingsTopBar() {
    val context = LocalContext.current // Access the current context

    Row(
        modifier = Modifier
            .fillMaxWidth() // Barra que ocupa todo el ancho
            .background(Color(94, 94, 94, 37)) // Color de fondo personalizado
            .padding(16.dp) // Padding de la barra
            .statusBarsPadding() // Add padding to avoid overlapping with system bar
        , verticalAlignment = Alignment.CenterVertically, // Centrar el contenido verticalmente
        horizontalArrangement = Arrangement.SpaceBetween // Distribuye los elementos equitativamente
    ) {
        // Título de la app
        Text(
            text = "Settings",
            style = MaterialTheme.typography.titleLarge,
            color = Color.Black
        )

    }
}


@Composable
fun SettingsScreen(innerPadding: PaddingValues, navController: NavController) {
    // LazyColumn con fondo semi-transparente
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0.1f, 0.1f, 0.1f, 0.9f)) // Fondo semi-transparente
                .padding(vertical = 20.dp)
        ) {
            item {
                Text(
                    text = "Settings Body",
                    color = Color.DarkGray,
                    fontSize = 20.sp
                )
            }
        }
    }
}