package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.pages.settings.SettingsScreen
import com.example.myapplication.ui.pages.settings.SettingsTopBar
import com.example.myapplication.ui.pages.Info.AboutTopBar
import com.example.myapplication.ui.pages.Info.InfoScreen
import com.example.myapplication.ui.pages.Info.ShareFloatingButton
import com.example.myapplication.ui.pages.gallery.AddFloatingButton
import com.example.myapplication.ui.pages.gallery.GalleryScreen
import com.example.myapplication.ui.pages.gallery.GalleryTopBar
import com.example.myapplication.ui.pages.gallery.GalleryViewModel
import com.example.myapplication.ui.pages.home.HomeScreen
import com.example.myapplication.login.LoginActivity
import com.example.myapplication.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseUser

class MainActivity : ComponentActivity() {

    // Inicializar la actividad
    override fun onCreate(savedInstanceState: Bundle?) {

        // Llamar a la superclase
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Inicializar el ViewModel
        val mainViewModel = MainViewModel(this)

        setContent {

            // Acceder al contexto actual
            val context = LocalContext.current
            // Observar el usuario actual
            val user: FirebaseUser? by mainViewModel.user.collectAsState(initial = null)
            // Observar eventos de navegación
            val navigationTarget: MainViewModel.NavigationTarget by mainViewModel.navigationTarget.observeAsState(
                MainViewModel.NavigationTarget.Main
            )

            // Observar los eventos de navegación
            LaunchedEffect(Unit) {
                // Observamos los eventos de navegación
                mainViewModel.navigationChannel.collect {
                    when(navigationTarget) {
                        MainViewModel.NavigationTarget.Main -> {
                            context.startActivity(Intent(context, MainActivity::class.java))
                        }
                        MainViewModel.NavigationTarget.Login -> {
                            context.startActivity(Intent(context, LoginActivity::class.java))
                        }
                        else -> {
                            context.startActivity(Intent(context, MainActivity::class.java))
                        }
                    }

                    (context as Activity).finish()
                }
            }

            // Configurar el contenido de la actividad
            AppTheme {

                // Mostrar la pantalla de inicio
                MainContent(mainViewModel)
            }
        }
    }
}

@Composable
fun MainContent(mainViewModel: MainViewModel) {
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
            composable("settings") { SettingsScreen(innerPadding, navController, mainViewModel) }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar() {
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
