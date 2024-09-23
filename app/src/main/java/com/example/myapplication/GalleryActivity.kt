package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MikelGreen
import com.example.myapplication.ui.theme.MyApplicationTheme

class GalleryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Gallery()
            }
        }
    }
}

fun sampleArtworks(): List<Artwork> {
    return listOf(
        Artwork(
            "Obra 1",
            "Título 1",
            "Descripción de la obra 1",
            "2023-01-01",
            ArtworkStyle.WATERCOLOUR
        ),
        Artwork(
            "Obra 2",
            "Título 2",
            "Descripción de la obra 2",
            "2023-02-01",
            ArtworkStyle.DIGITAL
        ),
        Artwork("Obra 3", "Título 3", "Descripción de la obra 3", "2023-03-01", ArtworkStyle.INK)
    )
}

@Preview(showBackground = true)
@Composable
fun GalleryPreview() {
    Gallery()
}

@Composable
fun Gallery() {
    val navController = rememberNavController()
    val isSingleColumn = remember { mutableStateOf(true) } // Para alternar entre una y dos columnas

    Scaffold(
        modifier = Modifier,
        topBar = {
            GalleryTopBar(isSingleColumn)
        },
        bottomBar = {
            BottomNavBar(navController)
        },
        floatingActionButton = {
            AddFloatingButton()
        }
    ) { innerPadding ->
        GalleryContent(innerPadding, isSingleColumn, sampleArtworks())
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
            .fillMaxSize()
    ) {
        Text(
            text = "${artwork.title} - ${artwork.style.name}",
            modifier = Modifier.padding(16.dp)
        )
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