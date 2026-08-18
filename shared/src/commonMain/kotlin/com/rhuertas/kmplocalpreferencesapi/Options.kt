package com.rhuertas.kmplocalpreferencesapi

enum class OptionColor {
    WHITE,
    GREEN,
    RED
}

data class Options(
    val color: OptionColor,
    val mode: Int,
    val dark_mode: Boolean
)

val DefaultOptions = Options(
    color = OptionColor.WHITE,
    mode = 0,
    dark_mode = false
)
