package com.example.myapplication

import GalleryViewModel
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
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
        modifier = Modifier, topBar = {
            when (current) {
                "gallery" -> GalleryTopBar(galleryViewModel.isSingleColumn)
                "settings" -> SettingsTopBar()
                "home" -> TopBar()
                "info" -> AboutTopBar()
                else -> TopBar()
            }
        }, bottomBar = {
            BottomNavBar(navController)
        }, floatingActionButton = {
            when (current) {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
    val context = LocalContext.current // Access the current context

    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        title = {
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Barra que ocupa todo el ancho
                    .padding(16.dp) // Padding de la barra
                    .statusBarsPadding() // Add padding to avoid overlapping with system bar
                ,
                verticalAlignment = Alignment.CenterVertically, // Centrar el contenido verticalmente
                horizontalArrangement = Arrangement.SpaceBetween // Distribuye los elementos equitativamente
            ) {
                // Título de la app
                Text(
                    text = "Main Activiy",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    )

}

@Composable
@Preview
fun BottomNavBarPreview() {
    val navController = rememberNavController()
    AppTheme {
        BottomNavBar(navController)
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {

    // Componente actual
    val actual = navController.currentBackStackEntry?.destination?.route
    val colors = NavigationBarItemDefaults.colors(
        selectedIconColor = MaterialTheme.colorScheme.secondaryContainer, // Color del ícono cuando está seleccionado
        unselectedIconColor = MaterialTheme.colorScheme.onSecondaryContainer, // Color del ícono cuando no está seleccionado
        selectedTextColor = MaterialTheme.colorScheme.secondaryContainer, // Color del texto cuando está seleccionado
        unselectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer, // Color del texto cuando no está seleccionado
        indicatorColor = MaterialTheme.colorScheme.onSecondaryContainer // Fondo cuando está seleccionado
    )

    NavigationBar(
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            selected = actual == "home",
            onClick = {
                navController.navigate("home") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = colors,
            modifier = Modifier
            .clip(RoundedCornerShape(50)) // Ajusta la forma del indicador
        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Info, contentDescription = "Info") },
            selected = actual == "info",
            onClick = {
                navController.navigate("info") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            },
            colors = colors

        )
        NavigationBarItem(
            icon = {
                Icon(
                    painterResource(R.drawable.baseline_gray_brush_24),
                    contentDescription = "Gallery"
                )
            },
            selected = actual == "gallery",
            onClick = {
                navController.navigate("gallery") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }, colors = colors

        )
        NavigationBarItem(
            icon = { Icon(Icons.Default.Settings, contentDescription = "Settings") },
            selected = actual == "settings",
            onClick = {
                navController.navigate("settings") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }, colors = colors

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
                .padding(vertical = 20.dp)
        ) {

        }
    }
}