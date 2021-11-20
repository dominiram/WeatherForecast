package app.naum.myapplication.repositories

import app.naum.myapplication.network.models.WeatherModel
import app.naum.myapplication.network.APIService
import app.naum.myapplication.network.models.WeatherListModel
import app.naum.myapplication.utils.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class WeatherRepo constructor(
    private val apiService: APIService
) {
    suspend fun getWeatherForCity(city: String): Flow<DataState<WeatherModel>> = flow {
        emit(DataState.Loading)

        try {
            val weatherModel = apiService.getWeatherForCity(city, API_KEY, "metric")
            emit(DataState.Success(weatherModel))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getWeatherListForCity(city: String): Flow<DataState<WeatherListModel>> = flow {
        emit(DataState.Loading)
        try {
            val weatherList = apiService.getWeatherListForCity(city, API_KEY, "metric", 5)
            emit(DataState.Success(weatherList))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    companion object {
        val API_KEY = "824b662880ceb611f94357717ff09768"
    }
}