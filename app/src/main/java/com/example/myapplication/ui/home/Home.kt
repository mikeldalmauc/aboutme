package com.example.myapplication.ui.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.ui.theme.AppTheme
import java.lang.Float.max
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.abs
import kotlin.math.min


@Composable
fun HomeScreen(innerPadding: PaddingValues, navController: NavController) {
    // LazyColumn con fondo semi-transparente
    val homeViewModel = HomeViewModel()

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
@Preview(showBackground = true)
fun HueSaturationValueScreenPreview() {
    AppTheme {
        HueSaturationValueScreen(HomeViewModel())
    }
}

@Composable
fun HueSaturationValueScreen(viewModel: HomeViewModel) {
    val hue: Float by viewModel.hue.observeAsState(initial = 100.0f)
    val saturation: Float by viewModel.saturation.observeAsState(initial = 1.0f)
    val value: Float by viewModel.value.observeAsState(initial = 0.5f)
    val alpha: Float by viewModel.alpha.observeAsState(initial = 1f)
    val color: Color by viewModel.color.observeAsState(initial = Color.Red)
    val argb: Int by viewModel.argbColor.observeAsState(initial = Color.Red.toArgb())
    val hex: String by viewModel.hex.observeAsState(initial = "#FF0000")
    val red: Float by viewModel.red.observeAsState(initial = 255f)
    val green: Float by viewModel.green.observeAsState(initial = 0f)
    val blue: Float by viewModel.blue.observeAsState(initial = 0f)
    val spaceColor: HomeViewModel.ColorSpace by viewModel.colorSpace.observeAsState(initial = HomeViewModel.ColorSpace.HSV)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .border(2.dp, MaterialTheme.colorScheme.outline, RectangleShape)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        InfoSection(
            hue,
            saturation,
            value,
            alpha,
            color,
            argb,
            hex,
            spaceColor
        ) { viewModel.changeColorSpace(it) }

        when (spaceColor) {
            HomeViewModel.ColorSpace.HSV -> {
                SliderSection("Hue", hue, hue, false) { viewModel.onHueChanged(it) }
                SliderSection("Saturation", saturation, hue, true) {
                    viewModel.onSaturationChanged(
                        it
                    )
                }
                SliderSection("Value", value, hue, true) { viewModel.onValueChanged(it) }
            }

            HomeViewModel.ColorSpace.RGB -> {
                SliderSection("Red", red, hue, false) { viewModel.onRedChanged(it) }
                SliderSection("Green", green, hue, false) { viewModel.onGreenChanged(it) }
                SliderSection("Blue", blue, hue, false) { viewModel.onBlueChanged(it) }
            }

            else -> {
                // No hacer nada
            }
        }

    }
}

@Composable
fun InfoSection(
    hue: Float,
    saturation: Float,
    value: Float,
    alpha: Float,
    color: Color,
    argb: Int,
    hex: String,
    colorSpace: HomeViewModel.ColorSpace,
    changeColorSpace: (HomeViewModel.ColorSpace) -> Unit
) {

    val clipboardManager: androidx.compose.ui.platform.ClipboardManager =
        LocalClipboardManager.current
    var expanded by remember { mutableStateOf(false) }
    val inverseColor = ajustarContraste(color)

    fun roundOffDecimal(number: Float): String {
        val df = DecimalFormat("#.###")
        df.roundingMode = RoundingMode.CEILING
        return df.format(number)
    }

    // Botón para expandir el menú
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopEnd
    )
    {
        // Botón estilo "TextButton" para integrarlo visualmente
        TextButton(
            onClick = { expanded = !expanded } // Cambiar el estado de expansión
            , elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 2.dp,
                pressedElevation = -2.dp
            )
            , shape = MaterialTheme.shapes.small
            , contentPadding = PaddingValues(2.dp)
            , colors = ButtonDefaults.textButtonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            )
          //  , modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Text(
                text = when (colorSpace) {
                    HomeViewModel.ColorSpace.HSV -> "HSV"
                    HomeViewModel.ColorSpace.RGB -> "RGB"
                }
                , color = MaterialTheme.colorScheme.onSecondaryContainer
                , style = MaterialTheme.typography.bodyLarge
                , modifier = Modifier.padding(4.dp)
            )
        }

        // DropdownMenu para seleccionar el espacio de color
        DropdownMenu(
            offset = DpOffset(260.dp, 0.dp), // Ajustar
            expanded = expanded, // Usar el estado para controlar la expansión
            onDismissRequest = { expanded = false }, // Cerrar el menú al hacer clic fuera de él
            modifier = Modifier.background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            DropdownMenuItem(
                text = { Text("HSV") },
                colors = MenuItemColors(
                    textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    leadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    trailingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.surfaceDim,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                onClick = {
                    changeColorSpace(HomeViewModel.ColorSpace.HSV)
                    expanded = false // Cerrar el menú después de seleccionar
                }
            )
            DropdownMenuItem(
                text = { Text("RGB") },
                colors = MenuItemColors(
                    textColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    leadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    trailingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    disabledTextColor = MaterialTheme.colorScheme.surfaceDim,
                    disabledLeadingIconColor = MaterialTheme.colorScheme.onSecondaryContainer,
                    disabledTrailingIconColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                onClick = {
                    changeColorSpace(HomeViewModel.ColorSpace.RGB)
                    expanded = false // Cerrar el menú después de seleccionar
                }
            )
        }
    }

    Box(
        contentAlignment = Alignment.BottomEnd,
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(3.dp)
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
                text = "Hue: ${roundOffDecimal(hue)}\nSaturation: ${roundOffDecimal(saturation)}\nValue: ${
                    roundOffDecimal(
                        value
                    )
                }\n" +
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                )
                {
                    Text(
                        text = "#${hex}",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.background(color),
                        color = inverseColor
                    )
                }
            }
        }

        // Botón para copiar el valor hexadecimal al portapapeles
        Image(
            painter = painterResource(id = R.drawable.baseline_content_copy_24),
            contentDescription = "Copy",
            modifier = Modifier
                .size(26.dp)
                .offset(y = -16.dp)
                .clickable {
                    clipboardManager.setText(AnnotatedString((hex)))
                },
            colorFilter = ColorFilter.lighting(MaterialTheme.colorScheme.onBackground, Color.Black)
        )
    }
}


@Composable
fun SliderSection(
    name: String,
    value: Float,
    hue: Float,
    hasDecimal: Boolean,
    onValueChange: (Float) -> Unit
) {
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
                    valueString.toFloatOrNull()?.let { value -> onValueChange(value) }
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
                    color = MaterialTheme.colorScheme.onBackground,
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
        CustomSlider(
            sliderRangeAndStep(name),
            sliderGradientBrush(name, hue),
            value,
            onChange = onValueChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSlider(
    rangeAndStep: Pair<ClosedFloatingPointRange<Float>, Int>,
    brush: Brush,
    value: Float,
    onChange: (Float) -> Unit
) {

    // Slider sin colores para que la pista personalizada sea visible
    Slider(
        value = value,
        onValueChange = { onChange(it) },
        valueRange = rangeAndStep.first,
        steps = rangeAndStep.second, // Para representar correctamente los 360 tonos
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp),
        colors = SliderDefaults.colors(
            thumbColor = Color.White, // Color del "thumb" del slider
            activeTrackColor = Color.Transparent, // Hacer transparente el track activo
            inactiveTrackColor = Color.Transparent, // Hacer transparente el track inactivo
        ),
        track = { DrawTrack(brush) }, // No mostrar el track por completo
        thumb = { CustomThumb() } // Personalizar el "thumb" del slider
    )
}


@Composable
fun DrawTrack(brush: Brush) {

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

fun sliderGradientBrush(name: String, hue: Float): Brush = when (name) {
    "Hue" -> hueGradient()
    "Saturation" -> saturationGradient(hue)
    "Value" -> valueGradient()
    "Red" -> colorGradient(0.0f)
    "Green" -> colorGradient(120.0f)
    "Blue" -> colorGradient(240.0f)
    else -> valueGradient()
}

fun sliderRangeAndStep(name: String): Pair<ClosedFloatingPointRange<Float>, Int> = when (name) {
    "Hue" -> Pair(0f..360f, 359)
    "Saturation" -> Pair(0f..1f, 359)
    "Value" -> Pair(0f..1f, 359)
    "Red" -> Pair(0f..255f, 255)
    "Green" -> Pair(0f..255f, 255)
    "Blue" -> Pair(0f..255f, 255)
    else -> Pair(0f..1f, 359)
}

fun colorGradient(hue: Float): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            Color.hsv(hue, 0f, 0.0f),
            Color.hsv(hue, 1f, 1f)
        )
    )
}

fun saturationGradient(hue: Float): Brush {
    return Brush.horizontalGradient(
        colors = listOf(
            Color.hsv(hue, 0f, 0.5f),
            Color.hsv(hue, 1f, 1f)
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
    val thumbBorderColor = MaterialTheme.colorScheme.onSurfaceVariant
    Canvas(
        modifier = Modifier
            .size(18.dp)
            .padding(4.dp)
            .offset(x = 0.dp, y = 10.dp)
            .pointerHoverIcon(PointerIcon.Hand, true)
    ) {
        val trianglePath = Path().apply {
            moveTo(size.width / 2f, 0f) // Punto inferior medio (punta)
            lineTo(0f, size.height) // Lado izquierdo
            lineTo(size.width, size.height) // Lado derecho
            close()
        }
        drawPath(trianglePath, color = thumbColor, style = Fill)
        drawPath(trianglePath, color = thumbBorderColor, style = Stroke(width = 3.dp.toPx()))
    }
}

fun ajustarContraste(fondo: Color): Color {
    // Calcular la luminosidad del color de fondo
    val r = fondo.red
    val g = fondo.green
    val b = fondo.blue
    val luminosidad = (0.299 * r + 0.587 * g + 0.114 * b)

    // Ajustar el contraste utilizando un coeficiente exponencial
    // para aumentar el contraste de manera más notoria cuando la luminosidad es baja
    val factorContraste = if (luminosidad < 0.5) {
        1 + (0.5 - luminosidad) * 2 // Ajuste para mayor contraste con fondo oscuro
    } else {
        1 - (luminosidad - 0.5) * 2 // Ajuste para mayor contraste con fondo claro
    }

    // Calcular el color contrario con mayor contraste
    val colorContrario = if (luminosidad < 0.5) Color.White else Color.Black
    val rContraste = min(1f, max(0f, (colorContrario.red * factorContraste).toFloat()))
    val gContraste = min(1f, max(0f, (colorContrario.green * factorContraste).toFloat()))
    val bContraste = min(1f, max(0f, (colorContrario.blue * factorContraste).toFloat()))

    return Color(rContraste, gContraste, bContraste)
}