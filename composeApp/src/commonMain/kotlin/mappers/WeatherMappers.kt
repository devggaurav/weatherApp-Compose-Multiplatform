package mappers

import domain.model.WeatherDataRealm
import domain.model.WeatherRealm
import domain.weather.WeatherData
import domain.weather.WeatherInfo
import domain.weather.WeatherType
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


//
// Created by Code For Android on 10/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

data class IndexedWeatherData(
    val index: Int,
    val data: WeatherData
)

fun WeatherRealm.toWeatherInfo(): WeatherInfo {

    val weatherDataMap = weatherData.toWeatherDataMap()
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if (now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }

    return WeatherInfo(
        weatherDataPerDay = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}


fun WeatherDataRealm.toWeatherDataMap(): Map<Int, List<WeatherData>> {

    return time.mapIndexed { index, time ->
        val tempreature = temperatures[index]
        val weatherCode = weatherCodes[index]
        val windSpeed = windSpeeds[index]
        val pressure = pressures[index]
        val humidity = humidities[index]
        IndexedWeatherData(
            index = index,
            data = WeatherData(
                time = time.toLocalDateTime(),
                temperatureCelsius = tempreature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { it.data }
    }

}