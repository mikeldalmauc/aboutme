package com.example.myapplication.ui.componentes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AddressFormatterScreen(viewModel: AdressFormatterViewModel) {

    // Observar el formato seleccionado y la dirección formateada
    val selectedFormat by remember { viewModel::selectedFormat }
    val formattedAddress = viewModel.getFormattedAddress()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.surfaceContainer),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Mostrar la dirección formateada
        Text(
            text = formattedAddress,
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Opciones de formato usando RadioButton
        FormatOption(
            label = "Original",
            selected = selectedFormat == AdressFormatterViewModel.FormatType.ORIGINAL,
            onSelect = { viewModel.onFormatSelected(AdressFormatterViewModel.FormatType.ORIGINAL) }
        )
        FormatOption(
            label = "Una línea",
            selected = selectedFormat == AdressFormatterViewModel.FormatType.ONE_LINE,
            onSelect = { viewModel.onFormatSelected(AdressFormatterViewModel.FormatType.ONE_LINE) }
        )
        FormatOption(
            label = "Multilínea",
            selected = selectedFormat == AdressFormatterViewModel.FormatType.MULTILINE,
            onSelect = { viewModel.onFormatSelected(AdressFormatterViewModel.FormatType.MULTILINE) }
        )
    }
}

// Composable para una opción de formato con RadioButton
@Composable
fun FormatOption(
    label: String,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth().padding(8.dp)
    ) {
        RadioButton(
            selected = selected,
            onClick = onSelect
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = label)
    }
}