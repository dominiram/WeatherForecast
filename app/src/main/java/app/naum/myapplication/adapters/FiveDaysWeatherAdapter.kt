package app.naum.myapplication.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import app.naum.myapplication.R
import app.naum.myapplication.databinding.ListItemFiveDaysWeatherBinding
import app.naum.myapplication.network.models.WeatherListModel
import app.naum.myapplication.network.models.WeatherModel
import com.bumptech.glide.Glide
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class FiveDaysWeatherAdapter(
    private val weatherList: WeatherListModel,
    private val context: Context
): Adapter<FiveDaysWeatherAdapter.ViewHolder>() {
    private lateinit var binding: ListItemFiveDaysWeatherBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemFiveDaysWeatherBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.list_item_five_days_weather, parent, false)
//        return ViewHolder(view)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val longDate = formatter.parse(weatherList.list[position].today)?.time

        if(longDate != null) {
            val date = Date(longDate)
            val c = Calendar.getInstance()
            c.time = date
            c.add(Calendar.DATE, position)
            val dayOfTheWeek = SimpleDateFormat("EEEE").format(c.time)
            binding.nameOfTheDay.text = dayOfTheWeek
        }

        binding.minTempValue.text = weatherList.list[position].main.tempMin.roundToInt().toString()
        binding.currTempValue.text = weatherList.list[position].main.temp.roundToInt().toString()
        binding.maxTempValue.text = weatherList.list[position].main.tempMax.roundToInt().toString()
        Glide.with(context)
            .load("http://openweathermap.org/img/wn/${weatherList.list[position].weather[0].icon}@2x.png")
            .into(binding.listItemIcon)
    }

    override fun getItemCount(): Int {
        return 5
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view)
}
