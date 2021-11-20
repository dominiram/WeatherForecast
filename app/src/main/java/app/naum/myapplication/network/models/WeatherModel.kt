package app.naum.myapplication.network.models

data class WeatherModel(
    val coord: Coordinate,
    val weather: List<WeatherInfo>,
    val base: String,
    val main: Temperature,
    val visibility: Int,
    val wind: Wind,
    val sys: Sys,
    val timezome: Int,
    val id: Int,
    val name: String,
    val cod: Int
)
