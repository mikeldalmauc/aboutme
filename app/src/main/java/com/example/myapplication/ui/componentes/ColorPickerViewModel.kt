package com.example.myapplication.ui.componentes;

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class ColorPickerViewModel : ViewModel() {

    // Live Data para el Hue Saturation Value Color picker

   /*
    hue - The color value in the range (0..360), where 0 is red, 120 is green, and 240 is blue
    saturation - The amount of hue represented in the color in the range (0..1), where 0 has no color and 1 is fully saturated.
    alpha - Alpha channel to apply to the computed color
    value - The strength of the color, where 0 is black.

    red - The red component of the color in the range (0..255)
    green - The green component of the color in the range (0..255)
    blue - The blue component of the color in the range (0..255)

    color - The color represented by the HSV values
    argbColor - The color represented by the RGB values
    hex - The color represented by the RGB values as a hexadecimal string
    */

    enum class ColorSpace {
        HSV,
        RGB
    }

    private val _colorSpace = MutableLiveData<ColorSpace>()
    val colorSpace: LiveData<ColorSpace> = _colorSpace

    private val _hue = MutableLiveData<Float>()
    val hue: LiveData<Float> = _hue

    private val _saturation = MutableLiveData<Float>()
    val saturation: LiveData<Float> = _saturation

    private val _value = MutableLiveData<Float>()
    val value: LiveData<Float> = _value

    private val _alpha = MutableLiveData<Float>()
    val alpha: LiveData<Float> = _alpha

    private val _red = MutableLiveData<Float>()
    val red: LiveData<Float> = _red

    private val _green = MutableLiveData<Float>()
    val green: LiveData<Float> = _green

    private val _blue = MutableLiveData<Float>()
    val blue: LiveData<Float> = _blue

    private val _color = MutableLiveData<Color>()
    val color: LiveData<Color> = _color

    private val _argbColor = MutableLiveData<Int>()
    val argbColor: LiveData<Int> = _argbColor

    private val _hex = MutableLiveData<String>()
    val hex: LiveData<String> = _hex

    init {
        _hue.value = 0f
        _saturation.value = 0.7f
        _value.value = 0.5f
        _alpha.value = 1f
        _colorSpace.value = ColorSpace.HSV
        updateRGBValues()
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun updateRGBValues(){
        val hue = _hue.value ?: 0f
        val saturation = _saturation.value ?: 0f
        val value = _value.value ?: 0f
        val alpha = _alpha.value ?: 0f

        _color.value = Color.hsv(hue, saturation, value, alpha)

        // Asegurarse de que _color.value no sea null antes de llamar a toArgb
        _color.value?.let { color ->
            _argbColor.value = color.toArgb()

            // Asegurarse de que _argbColor.value no sea null antes de llamar a toHexString
            _argbColor.value?.let { argb ->
                    _hex.value = argb.toHexString(format = HexFormat.UpperCase)

                // Obtener componentes RGB del color
                _blue.value = argb.blue.toFloat()
                _red.value = argb.red.toFloat()
                _green.value = argb.green.toFloat()
            }
        }
    }

    @OptIn(ExperimentalStdlibApi::class)
    fun updateHSVValues(){
        val red = _red.value ?: 0
        val green = _green.value ?: 0
        val blue = _blue.value ?: 0

        val color = Color(red.toInt(), green.toInt(), blue.toInt())
        _color.value = color
        _argbColor.value = color.toArgb()
        _hex.value = _argbColor.value!!.toHexString(format = HexFormat.UpperCase)

        val hsv = floatArrayOf(0f, 0f, 0f)
        android.graphics.Color.RGBToHSV(red.toInt(), green.toInt(), blue.toInt(), hsv)

        _hue.value = hsv[0]
        _saturation.value = hsv[1]
        _value.value = hsv[2]
    }

    fun onHueChanged(it: Float) {
        _hue.value =  it.coerceIn(0.0f, 360.0f)
        updateRGBValues()
    }

    fun onSaturationChanged(it: Float) {
        _saturation.value =  it.coerceIn(0.0f, 1.0f)
        updateRGBValues()
    }

    fun onValueChanged(it: Float) {
        _value.value =  it.coerceIn(0.0f, 1.0f)
        updateRGBValues()
    }

    fun onRedChanged(it: Float) {
        _red.value = it.coerceIn(0.0f, 255.0f)
        updateHSVValues()
    }

    fun onGreenChanged(it: Float) {
        _green.value = it.coerceIn(0.0f, 255.0f)
        updateHSVValues()
    }

    fun onBlueChanged(it: Float) {
        _blue.value = it.coerceIn(0.0f, 255.0f)
        updateHSVValues()
    }

    fun changeColorSpace(colorSpace: ColorSpace){
        _colorSpace.value = colorSpace
    }

    init {
        _hue.value = 0f
        _saturation.value = 0.7f
        _value.value = 0.5f
        _alpha.value = 1f
        _colorSpace.value = ColorSpace.HSV
        updateRGBValues()
    }
}
