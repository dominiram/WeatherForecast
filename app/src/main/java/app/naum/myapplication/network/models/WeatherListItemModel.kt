package app.naum.myapplication.network.models

import com.google.gson.annotations.SerializedName

data class WeatherListItemModel(
    val main: Temperature,
    val weather: List<WeatherInfo>,
    @SerializedName("dt_txt")
    val today: String
)
