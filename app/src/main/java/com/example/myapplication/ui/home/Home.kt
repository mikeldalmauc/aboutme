package com.example.myapplication.ui.home

import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    // LazyColumn con fondo semi-transparente
    var homeViewModel = HomeViewModel()

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
            item {
                Contador(homeViewModel)
                Spacer(modifier = Modifier.height(16.dp))
                HueSaturationValueScreen(homeViewModel)
            }
        }
    }
}

@Composable
fun Contador(viewModel: HomeViewModel) {
    // Obtener el valor del contador de LiveData
    val count: Int by viewModel.count.observeAsState(initial = 0)

    // Columna para organizar el contenido
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Texto que muestra el valor del contador
        Text(
            text = "Contador: $count",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp)) // Espacio entre el texto y los botones

        // Fila para organizar los botones de + y -
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botón de incrementar
            Button(onClick = { viewModel.upCount() }) {
                Text(text = "+")
            }

            // Botón de decrementar
            Button(onClick = { viewModel.downCount() }) {
                Text(text = "-")
            }
        }
    }
}


@Composable
fun HueSaturationValueScreen(viewModel: HomeViewModel){
    val hue: Float by viewModel.hue.observeAsState(initial = 0f)
    val saturation: Float by viewModel.saturation.observeAsState(initial = 1.0f)
    val value: Float by viewModel.value.observeAsState(initial = 0.5f)
    val alpha: Float by viewModel.alpha.observeAsState(initial = 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.hsv(hue, saturation, value, alpha))
                .fillMaxWidth()
                .height(200.dp)
                .padding(16.dp)
        ) {
        }
        Section("Hue", hue) { viewModel.onHueChanged(it) }
    }
}

@Composable
@Preview
fun SectionPreview() {
    Section(name = "Hue", value = 0f, onValueChange = {})
}

@Composable
fun Section(name: String, value: Float, onValueChange: (Float) -> Unit){
    Column (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Row (horizontalArrangement = Arrangement.SpaceEvenly){
            Text(text = "$name:")
            OutlinedTextField(
                value = value.toString()
                , onValueChange = { onValueChange(it.toFloatOrNull() ?: 0f) }
                , keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                , singleLine = true
                , maxLines = 1
                , modifier = Modifier.width(80.dp).height(40.dp).padding(5.dp)
                , colors =  TextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.surfaceDim,
                    errorIndicatorColor = MaterialTheme.colorScheme.onError
                )
            )
        }
        HueSlider(hue = value, onHueChange = { onValueChange(it) })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HueSlider(hue: Float, onHueChange: (Float) -> Unit){

        // Slider sin colores para que la pista personalizada sea visible
        Slider(
            value = hue,
            onValueChange = { onHueChange(it) },
            valueRange = 0f..360f,
            steps = 359, // Para representar correctamente los 360 tonos
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 0.dp),
            colors = SliderDefaults.colors(
                thumbColor = Color.White, // Color del "thumb" del slider
                activeTrackColor = Color.Transparent, // Hacer transparente el track activo
                inactiveTrackColor = Color.Transparent, // Hacer transparente el track inactivo
            ),
            track = { sliderState ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(12.dp) // Ajustar el tamaño
                        .drawWithCache {
                            // Crear un degradado multicolor horizontal
                            val gradient = Brush.horizontalGradient(
                                colors = List(361) { Color.hsv(it.toFloat(), 1f, 1f) }
                            )
                            onDrawBehind {
                                // Dibujar la pista del slider como una barra multicolor
                                drawRoundRect(
                                    brush = gradient,
                                    size = Size(size.width, 12.dp.toPx()), // Ajustar el grosor de la pista
                                )
                            }
                        }
                ){}
            }
            , // No mostrar el track por completo
            thumb = {
                // Dibujar el thumb personalizado como un triángulo invertido
                CustomThumb()
            }
        )
    }


@Composable
fun CustomThumb(){
    Canvas(modifier = Modifier.size(18.dp)
        .offset(x = 0.dp, y = 10.dp)) {
        val trianglePath = Path().apply {
            moveTo(size.width / 2f, 0f) // Punto inferior medio (punta)
            lineTo(0f, size.height) // Lado izquierdo
            lineTo(size.width, size.height) // Lado derecho
            close()
        }
        drawPath(trianglePath, color = Color.White) // Color del triángulo
    }
}