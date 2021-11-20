package app.naum.myapplication.network.models

import java.io.FileDescriptor

data class WeatherInfo(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)
