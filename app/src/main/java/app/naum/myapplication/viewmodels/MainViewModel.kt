package app.naum.myapplication.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.naum.myapplication.network.models.WeatherListModel
import app.naum.myapplication.network.models.WeatherModel
import app.naum.myapplication.repositories.WeatherRepo
import app.naum.myapplication.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepo: WeatherRepo
): ViewModel() {
    private val mutableWeatherModelState: MutableLiveData<DataState<WeatherModel>> = MutableLiveData()
    val weatherState: LiveData<DataState<WeatherModel>>
        get() = mutableWeatherModelState

    private val mutableListWeatherModelState: MutableLiveData<DataState<WeatherListModel>> = MutableLiveData()
    val weatherListState: LiveData<DataState<WeatherListModel>>
        get() = mutableListWeatherModelState

    fun getWeatherForCity(city: String) {
        viewModelScope.launch {
            weatherRepo.getWeatherForCity(city).collect {
                mutableWeatherModelState.value = it
            }
        }
    }

    fun getWeatherListForCity(city: String) {
        viewModelScope.launch {
            weatherRepo.getWeatherListForCity(city).collect {
                mutableListWeatherModelState.value = it
            }
        }
    }
}