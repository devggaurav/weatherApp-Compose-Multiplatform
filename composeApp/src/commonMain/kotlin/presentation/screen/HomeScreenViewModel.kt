package presentation.screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import domain.WeatherApiService
import domain.model.RequestState
import getPlatform
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import presentation.components.WeatherState
import util.LocationProvider
import util.PermissionHandler


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class HomeScreenViewModel(
    private val api: WeatherApiService,
    private val locationProvider: LocationProvider
) : ScreenModel {

    var state by mutableStateOf(WeatherState())
        private set

    var locationPermissionGranted by mutableStateOf(false)
        private set

    init {
        // checkPermissionAndFetchWeather()
    }


    fun checkPermissionAndFetchWeather() {

        screenModelScope.launch {
            if (locationProvider.requestLocationPermission()) {
                fetchWeather()
            }
        }


    }


    suspend fun fetchWeather() {


        screenModelScope.launch(Dispatchers.IO) {
            locationProvider.getLastKnownLocation()?.let {
                println("I am location ${it.latitude} ${it.longitude}")
                when (val result = api.getWeatherData(it.latitude, it.longitude)) {
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
}