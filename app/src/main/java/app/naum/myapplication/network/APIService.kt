package app.naum.myapplication.network

import app.naum.myapplication.network.models.WeatherListItemModel
import app.naum.myapplication.network.models.WeatherListModel
import app.naum.myapplication.network.models.WeatherModel
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("weather?") //weather?q={cityName}&appid={APIkey}
    suspend fun getWeatherForCity(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): WeatherModel

    @GET("forecast?")
    suspend fun getWeatherListForCity(
        @Query("q") cityName: String,
        @Query("appid") appId: String,
        @Query("units") unit: String,
        @Query("cnt") cnt: Int
    ): WeatherListModel
}
