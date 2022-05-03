package com.peter.weathercompose.widgets

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.peter.weathercompose.model.Favorite
import com.peter.weathercompose.model.WeatherItem
import com.peter.weathercompose.navigation.WeatherScreens
import com.peter.weathercompose.screens.favoutires.FavoritesViewModel
import com.peter.weathercompose.utils.formatDate
import com.peter.weathercompose.utils.formatDecimals

@Composable
 fun WeatherDetailsRow(weather: WeatherItem) {
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth(),
        shape = CircleShape.copy(CornerSize(6.dp)),
        color = Color.White
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(formatDate(weather.dt).split(",")[0], modifier = Modifier.padding(5.dp))
            WeatherStateImage(url = "https://openweathermap.org/img/wn/${weather.weather[0].icon}.png")
            Surface(
                modifier = Modifier.padding(0.dp), shape = CircleShape,
                color = Color(0xFFFFc400)
            ) {
                Text(
                    weather.weather[0].description,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(4.dp)
                )
            }

            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue.copy(alpha = 0.7f),
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(formatDecimals(weather.temp.max) + "º")

                }
                withStyle(
                    style = SpanStyle(
                        color = Color.LightGray
                    )
                ) {
                    append(formatDecimals(weather.temp.min) + "º")
                }
            })


        }

    }
}

@Composable
fun WeatherStateImage(url: String) {
    Image(painter = rememberImagePainter(url),
        contentDescription = "icon image" ,
        modifier = Modifier.size(80.dp))

}

@Composable
fun FavoriteCityRow(favorite: Favorite,navController: NavController,favoritesViewModel: FavoritesViewModel){
    Surface(
        Modifier
            .padding(3.dp)
            .fillMaxWidth()
            .height(50.dp)
            .clickable {
                navController.navigate(WeatherScreens.MainScreen.name +"/${favorite.city}")
            },
        shape = CircleShape.copy(CornerSize(6.dp)),
        color = Color(0xFFB2DFDB)) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            , horizontalArrangement = Arrangement.SpaceEvenly,) {

            Text(text = favorite.city, modifier = Modifier.padding(start = 4.dp))

            Surface(modifier = Modifier.padding(0.dp),
                shape = CircleShape,
                color = Color(0xFFD1E3E1)) {
                Text(text = favorite.country,
                    modifier = Modifier.padding(4.dp),
                    style = MaterialTheme.typography.caption)

            }
            Icon(imageVector = Icons.Rounded.Delete, contentDescription = "delete",
                modifier = Modifier.clickable {
                    favoritesViewModel.deleteFavorite(favorite)

                },
                tint = Color.Red.copy(alpha = 0.3f))
        }


    }



}