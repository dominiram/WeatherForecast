package app.naum.myapplication.views

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.naum.myapplication.adapters.FiveDaysWeatherAdapter
import app.naum.myapplication.databinding.ActivityMainBinding
import app.naum.myapplication.network.models.WeatherListModel
import app.naum.myapplication.network.models.WeatherModel
import app.naum.myapplication.utils.DataState
import app.naum.myapplication.viewmodels.MainViewModel
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception
import kotlin.math.roundToInt

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var weatherFinishedLoading = false
    private var fiveDaysWeatherFinishedLoading = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeToObservables()
        setupUI()
    }

    private fun subscribeToObservables() {
        viewModel.weatherState.observe(this, {
            when (it) {
                is DataState.Loading -> showLoadingIndicator(1)
                is DataState.Error -> showCityErrorState(it.exception)
                is DataState.Success -> showCitySuccessScreen(it.data)
            }
        })
        viewModel.weatherListState.observe(this, {
            when (it) {
                is DataState.Loading -> showLoadingIndicator(5)
                is DataState.Error -> showListErrorState(it.exception)
                is DataState.Success -> setupRecyclerView(it.data)
            }
        })
    }

    private fun setupUI() {
        binding.searchBtn.setOnClickListener {
            if (binding.cityNameEt.text.toString() == "") {
                Toast.makeText(
                    baseContext,
                    "You must enter city name!",
                    Toast.LENGTH_SHORT
                ).show()
            }

            viewModel.getWeatherForCity(binding.cityNameEt.text.toString())
            viewModel.getWeatherListForCity(binding.cityNameEt.text.toString())
        }
    }

    private fun showLoadingIndicator(numberOfDays: Int) {
        if(numberOfDays == 1)
            weatherFinishedLoading = false
        else fiveDaysWeatherFinishedLoading = false

        if (binding.spinKit.visibility != View.VISIBLE)
            binding.spinKit.visibility = View.VISIBLE
        hideWeatherInfo()
    }

    private fun showCityErrorState(e: Exception) {
        weatherFinishedLoading = true
        hideLoadingIndicator()
        e.printStackTrace()
        Toast.makeText(
            this,
            e.message,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showCitySuccessScreen(model: WeatherModel) {
        weatherFinishedLoading = true
        hideLoadingIndicator()
        Log.d(TAG, "showSuccessScreen: model = $model")
        Glide.with(this)
            .load("http://openweathermap.org/img/wn/${model.weather[0].icon}@2x.png")
            .into(binding.weatherIconIv)
        showWeatherInfo(model)
        val cityTitleText = "${model.name}, ${model.sys.country}"
        binding.cityTitle.text = cityTitleText

        binding.cityNameEt.hideKeyboard()
    }

    private fun showWeatherInfo(model: WeatherModel) {
        binding.weatherIconIv.visibility = View.VISIBLE
        binding.cityTitle.visibility = View.VISIBLE
        binding.cityWeatherInfo.root.visibility = View.VISIBLE

        populateCityWeatherValues(model)
    }

    private fun setupRecyclerView(weatherList: WeatherListModel) {
        Log.d(TAG, "setupRecyclerView: weatherList = $weatherList")
        binding.fiveDaysRv.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL,
            false
        )
        binding.fiveDaysRv.adapter = FiveDaysWeatherAdapter(
            weatherList,
            this
        )
        binding.fiveDaysRv.visibility = View.VISIBLE
        binding.cityNameEt.hideKeyboard()
        fiveDaysWeatherFinishedLoading = true
        hideLoadingIndicator()
    }

    private fun EditText.hideKeyboard(): Boolean {
        return (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(windowToken, 0)
    }

    private fun showListErrorState(e: Exception) {
        fiveDaysWeatherFinishedLoading = true
        hideLoadingIndicator()
        e.printStackTrace()
        Toast.makeText(
            this,
            e.message,
            Toast.LENGTH_SHORT
        ).show()
        binding.fiveDaysRv.visibility = View.GONE
    }

    private fun populateCityWeatherValues(model: WeatherModel) {
        binding.cityWeatherInfo.minTempValue.text = model.main.tempMin.roundToInt().toString()
        binding.cityWeatherInfo.currTempValue.text = model.main.temp.roundToInt().toString()
        binding.cityWeatherInfo.maxTempValue.text = model.main.tempMax.roundToInt().toString()
        binding.cityWeatherInfo.weatherInfoValue.text = model.weather[0].main
        binding.cityWeatherInfo.weatherDescriptionValue.text = model.weather[0].description
    }

    private fun hideWeatherInfo() {
        binding.weatherIconIv.visibility = View.GONE
        binding.cityTitle.visibility = View.GONE
        binding.cityWeatherInfo.root.visibility = View.GONE
        binding.fiveDaysRv.visibility = View.GONE
    }

    private fun hideLoadingIndicator() {
        if(weatherFinishedLoading && fiveDaysWeatherFinishedLoading)
            binding.spinKit.visibility = View.GONE
    }

    companion object {
        val TAG = "MainActivity"
    }
}