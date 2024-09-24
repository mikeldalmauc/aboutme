package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.text.googlefonts.Font
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


// Configuración de Google Fonts
val provider = GoogleFont.Provider(
    providerAuthority = "com.google.android.gms.fonts",
    providerPackage = "com.google.android.gms",
    certificates = R.array.com_google_android_gms_fonts_certs // Este certificado ya está incluido
)

// Familia de la fuente EB Garamond
val EB_Garamond = FontFamily(
    Font(
        googleFont = GoogleFont("EB Garamond"),
        fontProvider = provider,
        weight = FontWeight.ExtraBold
    )
)

// Familia de la fuente Montserrat con diferentes pesos
val Montserrat100 = FontFamily(
    Font(
        googleFont = GoogleFont("Montserrat"),
        fontProvider = provider,
        weight = FontWeight.W100 // Peso 100
    )
)

val Montserrat400 = FontFamily(
    Font(
        googleFont = GoogleFont("Montserrat"),
        fontProvider = provider,
        weight = FontWeight.W400 // Peso 400
    )
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = EB_Garamond,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 30.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Montserrat400,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Montserrat100,
        fontWeight = FontWeight.W100,
        fontSize = 12.sp
    )
)