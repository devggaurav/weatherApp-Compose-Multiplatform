package presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import getPlatform
import presentation.components.WeatherCard
import presentation.components.WeatherForeCast
import ui.theme.primaryColor
import ui.theme.surfaceColor
import ui.theme.surfaceDark
import ui.theme.surfaceLight
import util.LocationProvider


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class HomeScreen : Screen {


    @Composable
    override fun Content() {

        val viewModel = getScreenModel<HomeScreenViewModel>()
        LaunchedEffect(Unit) {
            viewModel.checkPermissionAndFetchWeather()
        }

        Box(
            modifier = Modifier.fillMaxSize()

        ) {

            Column(
                modifier = Modifier.fillMaxSize()
                    .background(surfaceDark).padding(top = 40.dp)
            ) {
                WeatherCard(viewModel.state)
                Spacer(modifier = Modifier.height(16.dp))
                WeatherForeCast(viewModel.state)
            }

            if (viewModel.state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            viewModel.state.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }


        }
    }
}