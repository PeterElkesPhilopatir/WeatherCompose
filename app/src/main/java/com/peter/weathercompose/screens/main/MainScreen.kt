package com.peter.weathercompose.screens.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.peter.weathercompose.R
import com.peter.weathercompose.data.WeatherResult
import com.peter.weathercompose.model.Weather
import com.peter.weathercompose.navigation.WeatherScreens
import com.peter.weathercompose.screens.settings.SettingsViewModel
import com.peter.weathercompose.utils.formatDate
import com.peter.weathercompose.utils.formatDateTime
import com.peter.weathercompose.utils.formatDecimals
import com.peter.weathercompose.widgets.WeatherAppBar
import com.peter.weathercompose.widgets.WeatherDetailsRow
import com.peter.weathercompose.widgets.WeatherStateImage
import kotlinx.coroutines.CoroutineScope

@Composable
fun MainScreen(
    navController: NavController,
    mainViewModel: MainViewModel = hiltViewModel(),
    city: String?,
    settingsViewModel: SettingsViewModel = hiltViewModel()
) {

    val unitFromDb = settingsViewModel.unitList.collectAsState().value
    var unit by remember {
        mutableStateOf("imperial")
    }
    var isImperial by remember {
        mutableStateOf(false)
    }

    if (!unitFromDb.isNullOrEmpty()) {
        unit = unitFromDb[0].unit.split(" ")[0].lowercase()
        isImperial = unit == "imperial"
        val weatherData = produceState<WeatherResult<Weather, Boolean, Exception>>(
            initialValue = WeatherResult(loading = true, data = null)
        ) {
            value = mainViewModel.getWeather(query = city.toString(), unit)
        }.value
        if (weatherData.loading == true)
            LinearProgressIndicator(modifier = Modifier.height(8.dp))
        else if (weatherData.data?.list?.isNotEmpty() == true) {
            Log.i("GetWeather", "Loading is false")
            MainScaffold(
                weather = weatherData.data!!, navController = navController,
                isImperial = isImperial
            )
        }
    }
}

@Composable
fun MainScaffold(navController: NavController, weather: Weather, isImperial: Boolean) {
    Scaffold(topBar = {
        WeatherAppBar(
            title = "${weather.city.name} , ${weather.city.country}",
            navController = navController,
            icon = Icons.Default.ArrowBack,
            isMainScreen = true,
            onAddActionClicked = {
                navController.navigate(WeatherScreens.SearchScreen.name)

            }

        ) {
            Log.i("Button", "Clicked")
        }
    }) {
        MainContent(data = weather, isImperial = isImperial)
    }
}

@Composable
fun MainContent(data: Weather, isImperial: Boolean) {
    Column(
        Modifier
            .padding(4.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Date(weather = data)
        BigCircle(weather = data)
        HumidityRow(weather = data, isImperial = isImperial)
        Divider()
        SunRiseRow(weather = data)
        Text(
            "This Week",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Bold
        )
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            color = Color(0xFFEEF1EF),
            shape = RoundedCornerShape(size = 14.dp)
        ) {
            LazyColumn(
                modifier = Modifier.padding(2.dp),
                contentPadding = PaddingValues(1.dp)
            ) {
                items(items = data.list) {
                    WeatherDetailsRow(it)
                }

            }
        }
    }
}

@Composable
private fun Date(weather: Weather) {
    Text(
        text = formatDate(weather.list[0].dt),
        style = MaterialTheme.typography.caption,
        color = MaterialTheme.colors.onSecondary,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.padding(6.dp)
    )
}

@Composable
private fun BigCircle(weather: Weather) {
    Surface(
        modifier = Modifier
            .padding(4.dp)
            .size(200.dp),
        shape = CircleShape,
        color = Color(0xFFFFC400)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            WeatherStateImage(url = "https://openweathermap.org/img/wn/${weather.list[0].weather[0].icon}.png")
            Text(
                text = formatDecimals(weather.list[0].temp.day) + "º",
                style = MaterialTheme.typography.h4,
                fontWeight = FontWeight.ExtraBold
            )
            Text(
                text = weather.list[0].weather[0].main,
                fontStyle = FontStyle.Italic
            )
        }
    }
}

@Composable
fun HumidityRow(weather: Weather, isImperial: Boolean) {
    Row(
        Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(modifier = Modifier.padding(4.dp)) {
            Icon(
                painter = painterResource(id = R.drawable.humidity),
                contentDescription = "humidity icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.list[0].humidity}%",
                style = MaterialTheme.typography.caption
            )

        }

        Row() {
            Icon(
                painter = painterResource(id = R.drawable.pressure),
                contentDescription = "pressure icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${weather.list[0].pressure} psi",
                style = MaterialTheme.typography.caption
            )

        }

        Row {
            Icon(
                painter = painterResource(id = R.drawable.wind),
                contentDescription = "wind icon",
                modifier = Modifier.size(20.dp)
            )
            Text(
                text = "${formatDecimals(weather.list[0].speed)} " + if (isImperial) "mph" else "m/s",
                style = MaterialTheme.typography.caption
            )

        }
    }
}

@Composable
fun SunRiseRow(weather: Weather) {
    Row(
        Modifier
            .padding(top = 15.dp, bottom = 6.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.sunrise),
                contentDescription = "sunrise",
                modifier = Modifier.size(30.dp)
            )
            Text(
                text = formatDateTime(weather.list[0].sunrise),
                style = MaterialTheme.typography.caption
            )

        }

        Row {

            Text(
                text = formatDateTime(weather.list[0].sunset),
                style = MaterialTheme.typography.caption
            )
            Image(
                painter = painterResource(id = R.drawable.sunset),
                contentDescription = "sunset",
                modifier = Modifier.size(30.dp)
            )


        }
    }
}

