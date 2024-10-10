package com.example.myapplication.ui.pages.home

import LoaderViewModel
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.ui.componentes.AddressFormatterScreen
import com.example.myapplication.ui.componentes.AdressFormatterViewModel
import com.example.myapplication.ui.componentes.ColorPickerViewModel
import com.example.myapplication.ui.componentes.Contador
import com.example.myapplication.ui.componentes.ContadorViewModel
import com.example.myapplication.ui.componentes.ColorPicker
import com.example.myapplication.ui.componentes.ProgressScreen
import com.example.myapplication.ui.componentes.ProgressScreen2
import com.example.myapplication.ui.componentes.ProgressScreen3
import com.example.myapplication.ui.componentes.TodoApp
import com.example.myapplication.ui.componentes.TodoViewModel


@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    // LazyColumn con fondo semi-transparente
    val homeViewModel = HomeViewModel()
    val todoViewModel = TodoViewModel()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        TodoApp(todoViewModel)
    }
}