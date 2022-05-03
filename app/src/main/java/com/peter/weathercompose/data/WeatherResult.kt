package com.peter.weathercompose.data

data class WeatherResult<T, Boolean, E: Exception>(
    var data: T? = null,
    var loading: Boolean? = null,
    var e: E? = null)