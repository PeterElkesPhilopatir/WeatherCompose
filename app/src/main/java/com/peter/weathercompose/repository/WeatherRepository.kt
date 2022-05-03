package com.peter.weathercompose.repository

import android.util.Log
import com.peter.weathercompose.data.WeatherResult
import com.peter.weathercompose.model.Weather
import com.peter.weathercompose.model.WeatherObject
import com.peter.weathercompose.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(
private val api: WeatherAPI
) {
    private val result =
        WeatherResult<Weather,
                Boolean,
                Exception>()

    suspend fun getWeather(query : String,units:String): WeatherResult<Weather, Boolean, java.lang.Exception> {
        try {
            result.loading = true
            result.data = api.getWeatherAsync(query, units = units).await()
            if (result.data.toString().isNotEmpty()) result.loading = false
        } catch (exception: Exception) {
            result.e = exception
            Log.e("Exc", "getWeather: ${result.e!!.localizedMessage}")
        }
        return result
    }

}
