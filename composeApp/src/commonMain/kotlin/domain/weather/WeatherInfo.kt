package domain.weather

import kotlinx.datetime.LocalDateTime


//
// Created by Code For Android on 10/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

data class WeatherInfo(
    val weatherDataPerDay : Map<Int,List<WeatherData>>,
    val currentWeatherData : WeatherData?

)



data class WeatherData(
    val time : LocalDateTime,
    val temperatureCelsius : Double,
    val weatherType : WeatherType,
    val pressure : Double,
    val windSpeed : Double,
    val humidity : Double,
)