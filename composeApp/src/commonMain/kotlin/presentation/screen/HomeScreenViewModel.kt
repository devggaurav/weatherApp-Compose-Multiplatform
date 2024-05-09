package presentation.screen

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.WeatherApiService
import kotlinx.coroutines.launch


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class HomeScreenViewModel(
    private val api: WeatherApiService
) : ScreenModel {

    init {
        screenModelScope.launch {
            fetchWeather()
        }
    }

    private suspend fun fetchWeather() {
        api.getWeatherData(28.799520,76.124420)
    }
}