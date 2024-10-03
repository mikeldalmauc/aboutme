package com.example.myapplication.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.ui.gallery.sampleArtworks

class HomeViewModel : ViewModel() {

    // LiveData para el contador
    private val _count = MutableLiveData<Int>()
    val count: LiveData<Int> = _count

    fun upCount() {
        _count.value = (_count.value ?: 0) + 1
    }

    fun downCount() {
        _count.value = (_count.value ?: 0) - 1
    }

    // Live Data para el Hue Saturation Value Color picker

   /*
    hue - The color value in the range (0..360), where 0 is red, 120 is green, and 240 is blue
    saturation - The amount of hue represented in the color in the range (0..1), where 0 has no color and 1 is fully saturated.
    alpha - Alpha channel to apply to the computed color
    value - The strength of the color, where 0 is black.
    colorSpace - The RGB color space used to calculate the Color from the HSV values.
    */
    private val _hue = MutableLiveData<Float>()
    val hue: LiveData<Float> = _hue

    private val _saturation = MutableLiveData<Float>()
    val saturation: LiveData<Float> = _saturation

    private val _value = MutableLiveData<Float>()
    val value: LiveData<Float> = _value

    private val _alpha = MutableLiveData<Float>()
    val alpha: LiveData<Float> = _alpha

    init {
        _hue.value = 0f
        _saturation.value = 0.7f
        _value.value = 0.5f
        _alpha.value = 1f
    }


    fun onHueChanged(it: Float) {
        // Asegurarse de que el valor sea un número válido
        val clampedValue = it.coerceIn(0.0f, 360.0f)
        _hue.value = clampedValue

    }

    fun onSaturationChanged(it: Float) {
        // Asegurarse de que el valor sea un número válido
        val clampedValue = it.coerceIn(0.0f, 1.0f)
        _saturation.value = clampedValue
    }

    fun onValueChanged(it: Float) {
        // Asegurarse de que el valor sea un número válido
        val clampedValue = it.coerceIn(0.0f, 1.0f)
        _value.value = clampedValue
    }
}