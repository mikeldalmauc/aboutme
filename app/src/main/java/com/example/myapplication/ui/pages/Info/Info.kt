package com.example.myapplication.ui.pages.Info

import android.content.Intent
import android.graphics.BlurMaskFilter
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.example.myapplication.R


@Composable
fun InfoScreen(innerPadding: PaddingValues, navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        // LazyColumn con fondo semi-transparente
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(vertical = 20.dp)
        ) {
            item {
                Profile()
                About()
                Social()
            }
        }
    }}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutTopBar() {
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
                , verticalAlignment = Alignment.CenterVertically, // Centrar el contenido verticalmente
                horizontalArrangement = Arrangement.SpaceBetween // Distribuye los elementos equitativamente
            ) {
                // Título de la app
                Text(
                    text = "About Me",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )

                // Ícono para enviar un correo
                IconButton(onClick = {
                    // Intent para abrir la aplicación de correo
                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        data = Uri.parse("mailto:") // Solo abre aplicaciones de correo
                        putExtra(
                            Intent.EXTRA_EMAIL,
                            arrayOf("mikeldalmauc@gmail.com")
                        ) // Email destinatario
                        putExtra(Intent.EXTRA_SUBJECT, "He visitado tu perfil!") // Asunto del correo
                    }

                    // Verifica si hay alguna aplicación que pueda manejar el intent
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent) // Inicia la aplicación de correo
                    }
                }) {
                    Image(
                        painter = painterResource(id = R.drawable.favicon_mikel),
                        contentDescription = "Enviar email",
                        modifier = Modifier
                            .size(35.dp) // Padding del ícono
                        , colorFilter = ColorFilter.lighting(Color.White, MaterialTheme.colorScheme.onSecondaryContainer)
                    )
                }
            }
        }
    )
}

@Composable
fun ShareFloatingButton() {
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .padding(2.dp)
            .clip(CircleShape) // Clipa el contenedor completo
            .background(MaterialTheme.colorScheme.tertiaryContainer)
    ) {
        IconButton(
            onClick = {
                // Create an intent to share a link
                val shareIntent = Intent(Intent.ACTION_SEND).apply {
                    type = "text/plain"
                    putExtra(Intent.EXTRA_SUBJECT, "Check out my website!")
                    putExtra(Intent.EXTRA_TEXT, "http://www.mikeldalmauc.com") // Your website URL
                }

                // Start the sharing activity
                startActivity(context, Intent.createChooser(shareIntent, "Share via"), null)
            }
        ) {
            Icon(
                imageVector = Icons.Default.Share,
                contentDescription = "Share",
                tint = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier.size(24.dp)
            )
        }
    }

}

@Composable
fun Profile() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally // Alinea todo el contenido al centro
    ) {
        Image(
            painter = painterResource(id = R.drawable.profile_picture),
            contentDescription = "Personal Profile Picture",
            modifier = Modifier
                .size(220.dp)
                .clip(CircleShape)
                .border(BorderStroke(6.dp, MaterialTheme.colorScheme.tertiary), shape = CircleShape)
        )
        HorizontalDivider(
            color = MaterialTheme.colorScheme.inversePrimary, thickness = 1.dp, modifier = Modifier
                .padding(vertical = 10.dp)
        )
        Text(
            text = "Mikel Dalmau",
            fontSize = 24.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        )
        HorizontalDivider(color = MaterialTheme.colorScheme.inversePrimary, thickness = 1.dp)
        Text(
            text = "I am a former Software Engineer now teaching the way of the keyboard to young guns " +
                    "with a passion for sports, all kinds of food, and painting.",
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            lineHeight = 24.sp
        )
    }
}

@Composable
fun About() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp), verticalArrangement = Arrangement.SpaceEvenly
    )
    { // Row for the social media icons
        Subject(R.drawable.sharp_school_24, "Education", "Software Engineer")
        Subject(R.drawable.round_sports_gymnastics_24, "Sport", "Crossfit")
        Subject(R.drawable.baseline_fastfood_24, "Favourite Food", "All of them")
        Subject(R.drawable.baseline_brush_24, "Hobby", "Digital and watercolor painting")

    }
}

@Composable
fun Subject(@DrawableRes icon: Int, title: String, body: String?) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(vertical = 5.dp, horizontal = 16.dp)
            .border(BorderStroke(Dp.Hairline, Color.White), shape = RoundedCornerShape(16.dp)),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = "Github Icon",
            modifier = Modifier
                .size(60.dp) // Ajusta el tamaño del ícono según el alto que desees
                .align(Alignment.CenterVertically)// Alinea verticalmente el ícon
                .offset(x = 5.dp, y = 0.dp), // Ajusta la posición del ícono
            colorFilter = ColorFilter.lighting(Color.White, MaterialTheme.colorScheme.tertiary)
        )
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.align(Alignment.CenterVertically) // Alinea verticalmente el texto
        ) {
            Text(
                text = title,
                fontSize = 24.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = body ?: "",
                fontSize = 16.sp,
                textAlign = TextAlign.Left,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun Social() {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp), horizontalArrangement = Arrangement.SpaceEvenly
    )
    { // Row for the social media icons
        Image(
            painter = painterResource(id = R.drawable.ic_github),
            contentDescription = "Github Icon",
            modifier = Modifier
                .size(45.dp)
                .clickable {
                    val linkedinUrl = "https://github.com/mikeldalmauc"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
                    context.startActivity(intent)
                },
            colorFilter = ColorFilter.lighting(Color.White, MaterialTheme.colorScheme.tertiary)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_linkedin),
            contentDescription = "LinkedIn Icon",
            modifier = Modifier
                .size(45.dp)
                .clickable {
                    val linkedinUrl = "https://www.linkedin.com/in/mkldalmau/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
                    context.startActivity(intent)
                },
            colorFilter = ColorFilter.lighting(Color.White, MaterialTheme.colorScheme.tertiary)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_instagram),
            contentDescription = "Twitter Icon",
            modifier = Modifier
                .size(45.dp)
                .clickable {
                    val linkedinUrl = "https://www.instagram.com/mikel_dalmau/"
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkedinUrl))
                    context.startActivity(intent)
                },
            colorFilter = ColorFilter.lighting(Color.White, MaterialTheme.colorScheme.tertiary)
        )
    }
}

@Composable
fun heartWithShadow() {
    Image(
        painter = painterResource(id = R.drawable.baseline_favorite_24),
        contentDescription = "Favorite",
        modifier = Modifier
            .size(80.dp) // Ajusta el tamaño según necesites
            // .clickable(onClick = onClick)
            .drawBehind {
                // Crear el Path basado en las coordenadas del pathData
                val heartPath = Path().apply {
                    moveTo(12f, 21.35f)
                    lineTo(10.55f, 20.03f)
                    cubicTo(5.4f, 15.36f, 2f, 12.28f, 2f, 8.5f)
                    cubicTo(2f, 5.42f, 4.42f, 3f, 7.5f, 3f)
                    cubicTo(9.24f, 3f, 10.91f, 3.81f, 12f, 5.09f)
                    cubicTo(13.09f, 3.81f, 14.76f, 3f, 16.5f, 3f)
                    cubicTo(19.58f, 3f, 22f, 5.42f, 22f, 8.5f)
                    cubicTo(22f, 12.28f, 18.6f, 15.36f, 13.45f, 20.04f)
                    lineTo(12f, 21.35f)
                    close()
                }

                // Escalar el Path para que coincida con el tamaño del ícono
                val scaleFactor = size.width / 24f // Escalar de acuerdo al tamaño del canvas
                scale(scale = scaleFactor, pivot = Offset.Zero) {
                    // Dibujar la sombra
                    val shadowColor = Color.Black.copy(alpha = 0.5f)
                    val offset = Offset(1.5f, 1.5f) // Ajusta la posición de la sombra
                    // Usa `drawIntoCanvas` para aplicar el blur a la sombra
                    drawIntoCanvas { canvas ->
                        val paint = android.graphics
                            .Paint()
                            .apply {
                                color = android.graphics.Color.BLACK
                                alpha = 128 // Nivel de transparencia
                                maskFilter = BlurMaskFilter(
                                    0.5f,
                                    BlurMaskFilter.Blur.NORMAL
                                ) // Aplicar desenfoque
                            }

                        // Dibujar el Path con sombra y desenfoque
                        canvas.nativeCanvas.translate(offset.x, offset.y)
                        canvas.nativeCanvas.drawPath(heartPath.asAndroidPath(), paint)
                    }
                }
            }
    )
}