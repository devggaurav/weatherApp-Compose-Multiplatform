package domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//


@Serializable
data class Weather(
    @SerialName("hourly")
    val weatherData: WeatherData
)


@Serializable
data class WeatherData(

    val time: List<String>,

    @SerialName("temperature_2m")
    val temperatures: List<Double>,

    @SerialName("weathercode")
    val weatherCodes: List<Int>,

    @SerialName("pressure_msl")
    val pressures: List<Double>,

    @SerialName("windspeed_10m")
    val windSpeeds: List<Double>,

    @SerialName("relativehumidity_2m")
    val humidities: List<Double>


)