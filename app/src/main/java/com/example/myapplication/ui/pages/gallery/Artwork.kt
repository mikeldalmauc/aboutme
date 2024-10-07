package com.example.myapplication.ui.pages.gallery

data class Artwork(
    val name: String,
    val title: String,
    val description: String,
    val creationDate: String,
    val style: ArtworkStyle,
    val imageResId: Int // ID del recurso de la imagen (JPG)
)

enum class ArtworkStyle {
    WATERCOLOUR,
    DIGITAL,
    INK
}