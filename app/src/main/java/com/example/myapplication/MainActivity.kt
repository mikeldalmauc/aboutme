package com.example.myapplication

import android.content.Intent
import android.icu.text.IDNA.Info
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainContent()
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainContentPreview() {
    MainContent()
}

@Composable
fun MainContent() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier,
        topBar = {
            TopBar()
        },
        bottomBar = {
            BottomNavBar(navController)
        }
    ) { innerPadding ->
        MainBody(innerPadding)
    }
}

@Composable
fun TopBar() {
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
            text = "Main Activiy",
            color = Color.DarkGray,
            fontSize = 20.sp
        )
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val currentActivity = context.javaClass.simpleName

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = currentActivity == "MainActivity", // Marcar como seleccionado si es MainActivity
            onClick = {
                // Navegar a la actividad principal (MainActivity)
                coroutineScope.launch {
                    context.startActivity(Intent(context, MainActivity::class.java))
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            selected = currentActivity == "InfoActivity", // Marcar como seleccionado si es MainActivity
            onClick = {
                // Navegar a la primera actividad (FirstActivity)
                coroutineScope.launch {
                    context.startActivity(Intent(context, InfoActivity::class.java))
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.baseline_gray_brush_24), contentDescription = "Gallery") },
            selected = currentActivity == "GalleryActivity", // Marcar como seleccionado si es MainActivity
            onClick = {
                // Navegar a la segunda actividad (SecondActivity)
                coroutineScope.launch {
                    context.startActivity(Intent(context, GalleryActivity::class.java))
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = currentActivity == "SettingsActivity", // Marcar como seleccionado si es MainActivity
            onClick = {
                // Navegar a la tercera actividad (ThirdActivity)
                coroutineScope.launch {
                    context.startActivity(Intent(context, SettingsActivity::class.java))
                }
            }
        )
    }
}

@Composable
fun MainBody(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(paddingValues = paddingValues)
            .fillMaxSize()
    ) {
        // LazyColumn con fondo semi-transparente
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0.1f, 0.1f, 0.1f, 0.9f)) // Fondo semi-transparente
                .padding(vertical = 20.dp)
        ) {

        }
    }
}