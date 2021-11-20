package app.naum.myapplication.network.models

data class WeatherListModel(
    val cnt: Int,
    val list: List<WeatherListItemModel>,
    val city: CityModel
)
