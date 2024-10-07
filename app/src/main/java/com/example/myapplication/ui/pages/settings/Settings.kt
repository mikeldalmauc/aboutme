package com.example.myapplication.ui.pages.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.MainViewModel
import com.google.firebase.auth.FirebaseUser


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsTopBar() {
    val context = LocalContext.current // Access the current context

    TopAppBar(
        colors = topAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            titleContentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
        title = {
            // Título de la app
            Text(
                text = "Settings",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
    )
}


@Composable
fun SettingsScreen(
    innerPadding: PaddingValues,
    navController: NavController,
    mainViewModel: MainViewModel
) {

    val user: FirebaseUser? by mainViewModel.user.collectAsState(initial = null)


    // LazyColumn con fondo semi-transparente
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp)
        ) {
            item {
                Text(
                    text = "Logged in as " + user?.email,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 20.sp
                )

                LogoutButton(logout = { mainViewModel.logout() })
            }
        }
    }
}

@Composable
fun LogoutButton(logout: () -> Unit) {
    Button(
        onClick = { logout() },
        shape = MaterialTheme.shapes.large,
        modifier = Modifier
            .padding(top = 16.dp)
            .height(56.dp) // Hacer el botón más alto
            .width(270.dp), // Ajustar el botón para que ocupe todo el ancho
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.errorContainer // Usar el color secundario
            ,
            contentColor = MaterialTheme.colorScheme.onErrorContainer // Texto contrastan
            ,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceDim // Usar el color secundario
            ,
            disabledContentColor = MaterialTheme.colorScheme.onSurface // Texto contrastan
        ),
        elevation = ButtonDefaults.elevatedButtonElevation(
            defaultElevation = 8.dp, // Definir la elevación del botón
            pressedElevation = 12.dp, // Definir la elevación cuando es presionado
            disabledElevation = 0.dp
        )
    ) {
        Text(
            "Logout", // Cambia el texto si es necesario,
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineLarge, // Usar estilo de texto del tema
            color = MaterialTheme.colorScheme.onSecondary // Usar color de texto del tema
        )
    }

}
