package com.example.myapplication.ui.componentes

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdressFormatterViewModel : ViewModel() {

    // Estado para guardar la dirección de prueba
    private val _address = MutableLiveData<String>()


    init {
        _address.value = "1234 Calle Falsa, Piso 5, Ciudad Ejemplo, País Ficticio, 12345"
    }

    // Estado para guardar el formato seleccionado
    var selectedFormat by mutableStateOf(FormatType.ORIGINAL)
        private set

    // Función para cambiar el formato
    fun onFormatSelected(format: FormatType) {
        selectedFormat = format
    }

    // Enumeración de los tipos de formato
    enum class FormatType {
        ORIGINAL, ONE_LINE, MULTILINE
    }

    // Función para obtener la dirección formateada
    fun getFormattedAddress(): String {
        val address = _address.value ?: return ""
        return when (selectedFormat) {
            FormatType.ORIGINAL -> address
            FormatType.ONE_LINE -> address.replace(",", " -")
            FormatType.MULTILINE -> address.replace(",", "\n")
        }
    }
}