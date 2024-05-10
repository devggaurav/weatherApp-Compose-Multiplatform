package domain

import domain.model.RequestState
import domain.weather.WeatherInfo


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

interface WeatherApiService {

    suspend fun getWeatherData(lat: Double, lon : Double) : RequestState<WeatherInfo>
}