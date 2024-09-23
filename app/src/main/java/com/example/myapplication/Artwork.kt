package com.example.myapplication

data class Artwork(
    val name: String,
    val title: String,
    val description: String,
    val creationDate: String,
    val style: ArtworkStyle
)

enum class ArtworkStyle {
    WATERCOLOUR,
    DIGITAL,
    INK
}