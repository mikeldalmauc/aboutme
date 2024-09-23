package com.example.myapplication

import GalleryViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MikelGreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MyApplicationTheme {
                val galleryViewModel: GalleryViewModel = viewModel()
                Gallery(galleryViewModel)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GalleryPreview() {
    val galleryViewModel: GalleryViewModel = viewModel() // Crear un ViewModel para la vista previa

    Gallery( galleryViewModel)
}

@Composable
fun Gallery(viewModel: GalleryViewModel) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier,
        topBar = {
            GalleryTopBar(viewModel.isSingleColumn)
        },
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            AddFloatingButton()
        }
    ) { innerPadding ->
        GalleryContent(innerPadding, viewModel.isSingleColumn, viewModel.artworks.value)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GalleryTopBar(isSingleColumn: MutableState<Boolean>) {
    androidx.compose.material3.TopAppBar(
        title = { Text("Galería") },
        actions = {
            IconButton(onClick = {
                isSingleColumn.value = !isSingleColumn.value // Alternar entre una y dos columnas
            }) {
                Icon(
                    painterResource(id = if (isSingleColumn.value) R.drawable.baseline_view_list_24 else R.drawable.baseline_grid_view_24),
                    contentDescription = "Cambiar vista"
                )
            }
        }
    )
}

@Composable
fun GalleryContent(
    paddingValues: PaddingValues,
    isSingleColumn: MutableState<Boolean>,
    artworks: List<Artwork>
) {
    LazyVerticalGrid(
        columns = if (isSingleColumn.value) GridCells.Fixed(1) else GridCells.Fixed(2), // Cambiar el número de columnas
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0.1f, 0.1f, 0.1f, 0.9f))
            .padding(paddingValues)
    ) {
        items(
            count = artworks.size, // Número de elementos en la lista
            key = { index -> artworks[index].name }, // Clave única para cada elemento
        ) { index ->
            val artwork = artworks[index]
            ArtworkCard(artwork)
        }
    }

}

@Composable
fun ArtworkCard(artwork: Artwork) {

    Box(
        modifier = Modifier
            .padding(8.dp)
            .background(Color.LightGray)
            .fillMaxWidth() // Ajustar el tamaño de la tarjeta
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Imagen de la obra de arte
            Image(
                painter = painterResource(id = artwork.imageResId),
                contentDescription = "Imagen de la obra de arte",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp) // Ajusta el tamaño según lo que desees
                    .padding(8.dp)
            )

            // Texto con título y estilo de la obra
            Text(
                text = artwork.title,
                fontSize = 18.sp,
                modifier = Modifier.padding(8.dp)
            )

            Text(
                text = artwork.style.name,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
            )
        }
    }
}


@Composable
fun AddFloatingButton() {
    val context = LocalContext.current
    IconButton(
        onClick = {
            val intent = Intent(
                context,
                CreateGalleryItemActivity::class.java
            ) // Nueva actividad para crear o editar
            context.startActivity(intent)
        }, modifier = Modifier
            .background(MikelGreen, CircleShape) // Fondo del botón
            .padding(2.dp) // Padding del botón
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Agregar obra de arte"
        )
    }
}