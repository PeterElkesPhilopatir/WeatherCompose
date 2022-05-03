package com.peter.weathercompose.screens.main

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peter.weathercompose.data.WeatherResult
import com.peter.weathercompose.model.Weather
import com.peter.weathercompose.model.WeatherObject
import com.peter.weathercompose.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: WeatherRepository) : ViewModel() {

    val weather: MutableState<WeatherResult<Weather,
            Boolean, Exception>> = mutableStateOf(
        WeatherResult(null, true, Exception(""))
    )

    suspend fun getWeather(query: String,units:String): WeatherResult<Weather, Boolean, Exception> =
         repository.getWeather(query = query,units)


}