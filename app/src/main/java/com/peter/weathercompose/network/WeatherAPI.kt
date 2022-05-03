package com.peter.weathercompose.network

import com.peter.weathercompose.model.Weather
import com.peter.weathercompose.model.WeatherObject
import com.peter.weathercompose.utils.Constants.API_KEY
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {
    @GET("data/2.5/forecast/daily")
    fun getWeatherAsync(
        @Query("q") query: String,
        @Query("units") units: String = "imperial",
        @Query("appid") apikey: String = API_KEY
    ):
            Deferred<Weather>
}