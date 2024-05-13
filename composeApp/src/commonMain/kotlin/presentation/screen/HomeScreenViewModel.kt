package presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.WeatherApiService
import domain.model.RequestState
import kotlinx.coroutines.launch
import presentation.components.WeatherState


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class HomeScreenViewModel(
    private val api: WeatherApiService
) : ScreenModel {

    var state by mutableStateOf(WeatherState())
        private set

    init {
        screenModelScope.launch {
            fetchWeather()
        }
    }

    private suspend fun fetchWeather() {
        screenModelScope.launch {

            when (val result = api.getWeatherData(28.799520, 76.124420)) {
                is RequestState.Error -> {
                    state = state.copy(
                        weatherInfo = null,
                        isLoading = false,
                        error = result.getErrorMessage()
                    )
                }

                RequestState.Idle -> {}
                RequestState.Loading -> {
                    state = state.copy(
                        isLoading = true,
                        error = null
                    )
                }

                is RequestState.Success -> {
                    state = state.copy(
                        weatherInfo = result.getSuccessData(),
                        isLoading = false,
                        error = null
                    )
                }
            }


        }


    }
}