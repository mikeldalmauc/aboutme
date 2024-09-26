package com.example.myapplication

import GalleryViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    val galleryViewModel: GalleryViewModel = viewModel()
    val current = navController.currentBackStackEntryAsState().value?.destination?.route
    Scaffold(
        modifier = Modifier
        ,topBar = {
            when(current) {
                "gallery" -> GalleryTopBar(galleryViewModel.isSingleColumn)
                "settings" -> SettingsTopBar()
                "home" -> TopBar()
                "info" -> AboutTopBar()
                else -> TopBar()
            }
        }
        ,bottomBar = {
            BottomNavBar(navController)
        }
        ,floatingActionButton = {
            when(current) {
                "gallery" -> AddFloatingButton()
                "info" -> ShareFloatingButton()
                else -> {}
            }
        }
    ) { innerPadding ->
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(innerPadding, navController) }
                composable("info") { InfoScreen(innerPadding, navController) }
                composable("gallery") { GalleryScreen(innerPadding, galleryViewModel, navController) }
                composable("settings") { SettingsScreen(innerPadding, navController) }
            }
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
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = navController.currentBackStackEntry?.destination?.route == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            selected = navController.currentBackStackEntry?.destination?.route == "info",
            onClick = {
                navController.navigate("info") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(painterResource(R.drawable.baseline_gray_brush_24), contentDescription = "Gallery") },
            selected = navController.currentBackStackEntry?.destination?.route == "gallery",
            onClick = {
                navController.navigate("gallery") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = navController.currentBackStackEntry?.destination?.route == "settings",
            onClick = {
                navController.navigate("settings") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
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

        }
    }
}