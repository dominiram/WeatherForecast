package app.naum.myapplication.network.models

import com.google.gson.annotations.SerializedName

data class Temperature(
    val temp: Double,
    @SerializedName("feels_like")
    val feelsLike: Double,
    @SerializedName("temp_min")
    val tempMin: Double,
    @SerializedName("temp_max")
    val tempMax: Double,
    val pressure: Double,
    val humidity: Double,
)
