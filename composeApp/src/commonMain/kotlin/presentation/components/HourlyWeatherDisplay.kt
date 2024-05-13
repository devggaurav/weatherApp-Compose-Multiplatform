package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import domain.weather.WeatherData
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


//
// Created by Code For Android on 13/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HourlyWeatherDisplay(
    weatherData: WeatherData,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White
) {

    val formattedTime = remember(weatherData) {
        val hour =
            if (weatherData.time.hour < 10) "0${weatherData.time.hour}" else weatherData.time.hour
        val min =
            if (weatherData.time.minute < 10) "0${weatherData.time.minute}" else weatherData.time.minute


        " ${hour}:${min}"
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = formattedTime, color = Color.LightGray)
        Image(
            painter = painterResource(resource = weatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = Modifier.width(40.dp)
        )
        Text(
            text = "${weatherData.temperatureCelsius} °C",
            color = textColor,
            fontWeight = FontWeight.Bold
        )


    }


}