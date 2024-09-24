package com.example.myapplication

import GalleryViewModel
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MikelGold
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
        title = {
            Text("Galería"
                , style = MaterialTheme.typography.titleLarge,
                )
                },
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

    // Selección del ícono según el estilo de la obra
    val iconResId = when (artwork.style) {
        ArtworkStyle.WATERCOLOUR -> R.drawable.outline_palette_24
        ArtworkStyle.DIGITAL -> R.drawable.outline_video_stable_24
        ArtworkStyle.INK -> R.drawable.outline_ink_pen_24
    }

    Box(
        modifier = Modifier
            .padding(6.dp) // Padding alrededor de la tarjeta
            .shadow(8.dp, RoundedCornerShape(16.dp)) // Sombra con bordes redondeados
            .background(Color.White, RoundedCornerShape(16.dp)) // Fondo blanco y bordes redondeados
            .border(2.dp, MikelGold, RoundedCornerShape(16.dp)) // Borde fino
            .fillMaxWidth() // Ajustar el tamaño de la tarjeta
    ) {
        Column () {
            // Contenedor para superponer el icono sobre la imagen
            Box {
                // Imagen de la obra de arte
                Image(
                    painter = painterResource(id = artwork.imageResId),
                    contentDescription = "Imagen de la obra de arte",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth() // Llenar el ancho
                        .aspectRatio(1f) // Relación de aspecto 1:1 para que sea cuadrada
                        .clip(RoundedCornerShape(16.dp)) // Redondear la imagen
                )

                // Fondo para el icono (recuadro gris oscuro)
                Box(
                    modifier = Modifier
                        .size(36.dp) // Tamaño del recuadro
                        .background(Color(0xFF2C2C2C), RoundedCornerShape(8.dp)) // Color gris oscuro con bordes redondeados
                        .align(Alignment.TopEnd) // Alinear en la esquina superior derecha
                        .padding(8.dp) // Padding para ajustar la posición
                ) {

                    // Icono superpuesto
                    Icon(
                        painter = painterResource(id = iconResId),
                        contentDescription = "Icono del estilo de la obra de arte",
                        modifier = Modifier
                            .size(24.dp) // Tamaño del icono
                            .align(Alignment.Center), // Centrar el icono dentro del recuadro
                        tint = Color.White // Color del icono
                    )
                }
            }

            // Fondo para el titulo
            Row(
                modifier = Modifier
                    .fillMaxWidth() // Llenar el ancho
                    .background(Color(0xFF2C2C2C), RoundedCornerShape(8.dp)) // Color gris oscuro con bordes redondeados
                    .padding(8.dp) // Padding para ajustar la posición
            ) {
                // Título de la obra de arte
                Text(
                    text = artwork.title,
                    style = MaterialTheme.typography.bodyLarge, // Usando la fuente EB Garamond definida en el tema
                    fontSize = 18.sp,
                    modifier = Modifier
                        .padding(8.dp) // Padding alrededor del título,
                    , color = Color.White // Color del texto
                )
            }

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