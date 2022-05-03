package com.peter.weathercompose.model

import com.peter.weathercompose.model.City

data class Weather(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<WeatherItem>,
    val message: Double
)