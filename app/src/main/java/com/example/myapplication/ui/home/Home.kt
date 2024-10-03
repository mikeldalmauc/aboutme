package com.example.myapplication.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.navigation.NavController
import com.example.myapplication.ui.theme.AppTheme
import java.math.RoundingMode
import java.text.DecimalFormat


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
            .padding(32.dp)
            .border(2.dp, MaterialTheme.colorScheme.outline, RectangleShape),
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
fun HueSaturationValueScreen(viewModel: HomeViewModel) {
    val hue: Float by viewModel.hue.observeAsState(initial = 100.0f)
    val saturation: Float by viewModel.saturation.observeAsState(initial = 1.0f)
    val value: Float by viewModel.value.observeAsState(initial = 0.5f)
    val alpha: Float by viewModel.alpha.observeAsState(initial = 1f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(2.dp, MaterialTheme.colorScheme.outline, RectangleShape)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        InfoSection(hue, saturation, value, alpha)
        SliderSection("Hue", hue, false) { viewModel.onHueChanged(it) }
        SliderSection("Saturation", saturation, true) { viewModel.onSaturationChanged(it) }
        SliderSection("Value", value, true) { viewModel.onValueChanged(it) }
    }
}

@OptIn(ExperimentalStdlibApi::class)
@Composable
fun InfoSection(hue: Float, saturation: Float, value: Float, alpha: Float) {

    val color = Color.hsv(hue, saturation, value, alpha)
    val argb = color.toArgb()
    val hex = argb.toHexString(format = HexFormat.UpperCase)

    fun roundOffDecimal(number: Float): String {
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number)
    }

    Row(

    ) {
        Box(
            modifier = Modifier
                .background(color)
                .size(100.dp)
                .padding(16.dp)
                .clip(CircleShape),
        ) {
        }
        Text(
            text = "Hue: ${roundOffDecimal(hue)}\nSaturation: ${roundOffDecimal(saturation)}\nValue: ${roundOffDecimal(value)}\n" +
                    "Alpha: $alpha",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(16.dp)
        )
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {

            Text(
                text = "R: ${argb.red}\nG: ${argb.green}\nB: ${argb.blue}",
                style = MaterialTheme.typography.bodyMedium
            )
            Row()
            {

                Text(
                    text = "#${hex}",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.background(color),
                    color = Color(255 - argb.red, 255 - argb.green, 255 - argb.blue)
                )
            }
        }
    }
}

@Composable
fun SliderSection(name: String, value: Float, hasDecimal: Boolean, onValueChange: (Float) -> Unit) {
    val valueClamped = if (hasDecimal) value else value.toInt()

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom,
            modifier = Modifier.fillMaxWidth()

        ) {
            Text(text = "$name:")
            OutlinedTextField(
                value = valueClamped.toString(),
                onValueChange = { valueString ->
                    valueString.toFloatOrNull()?.let { value -> onValueChange(value.toFloat()) }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                maxLines = 1,
                modifier = Modifier
                    .width(60.dp)
                    .height(45.dp)
                    .padding(0.dp),
                readOnly = false,
                textStyle = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 12.sp,
                    lineHeight = 12.sp
                ),
                enabled = true,
                colors = OutlinedTextFieldDefaults.colors(
                    disabledTextColor = MaterialTheme.colorScheme.onSurfaceVariant, // Color del texto deshabilitado
                    cursorColor = MaterialTheme.colorScheme.primary, // Color del cursor
                    focusedBorderColor = MaterialTheme.colorScheme.primary, // Color del borde cuando está enfocado
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurfaceVariant, // Color del borde cuando no está enfocado
                    errorBorderColor = MaterialTheme.colorScheme.error, // Color del borde cuando hay un error
                    focusedLabelColor = MaterialTheme.colorScheme.primary, // Color del label cuando está enfocado
                    unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant // Color del label cuando no está enfocado
                    // Puedes agregar otros colores si es necesario
                )
                // Ajuste de padding interno del `OutlinedTextField`

            )

        }
        CustomSlider(name, value, onChange = onValueChange)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(name: String, value: Float, onChange: (Float) -> Unit) {

    val pair = when (name) {
        "Hue" -> Pair(0f..360f, 359)
        "Saturation" -> Pair(0f..1f, 359)
        "Value" -> Pair(0f..1f, 359)
        else -> Pair(0f..1f, 359)
    }

    // Slider sin colores para que la pista personalizada sea visible
    Slider(
        value = value,
        onValueChange = { onChange(it) },
        valueRange = pair.first,
        steps = pair.second, // Para representar correctamente los 360 tonos
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color.White, // Color del "thumb" del slider
            activeTrackColor = Color.Transparent, // Hacer transparente el track activo
            inactiveTrackColor = Color.Transparent, // Hacer transparente el track inactivo
        ),
        track = { DrawTrack(name, value) }, // No mostrar el track por completo
        thumb = { CustomThumb() } // Personalizar el "thumb" del slider
    )
}


@Composable
fun DrawTrack(type: String, value: Float) {

    val brush = when (type) {
        "Hue" -> hueGradient()
        "Saturation" -> saturationGradient()
        "Value" -> valueGradient()
        else -> valueGradient()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(12.dp) // Ajustar el tamaño
            .drawWithCache {
                // Crear un degradado multicolor horizontal
                onDrawBehind {
                    // Dibujar la pista del slider como una barra multicolor
                    drawRoundRect(
                        brush = brush,
                        size = Size(size.width, 12.dp.toPx()), // Ajustar el grosor de la pista
                    )
                }
            }
    ) {}
}

fun saturationGradient(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            Color.hsv(0f, 0f, 1f),
            Color.hsv(240f, 1f, 1f)
        )
    )
}

fun hueGradient(): Brush {
    return Brush.horizontalGradient(
        colors = List(361) { Color.hsv(it.toFloat(), 1f, 1f) }
    )
}

fun valueGradient(): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            Color.hsv(0f, 0.0f, 0.0f),
            Color.hsv(0f, 0.0f, 1.0f)
        )
    )
}

@Composable
fun CustomThumb() {
    val thumbColor = MaterialTheme.colorScheme.onBackground
    Canvas(
        modifier = Modifier
            .size(18.dp)
            .offset(x = 0.dp, y = 10.dp)
    ) {
        val trianglePath = Path().apply {
            moveTo(size.width / 2f, 0f) // Punto inferior medio (punta)
            lineTo(0f, size.height) // Lado izquierdo
            lineTo(size.width, size.height) // Lado derecho
            close()
        }
        drawPath(trianglePath, color = thumbColor, style = Stroke(width = 3.dp.toPx()))
    }
}

