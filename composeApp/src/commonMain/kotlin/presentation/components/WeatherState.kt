package presentation.components

import domain.weather.WeatherInfo


//
// Created by Code For Android on 13/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

data class WeatherState(
    val weatherInfo: WeatherInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
