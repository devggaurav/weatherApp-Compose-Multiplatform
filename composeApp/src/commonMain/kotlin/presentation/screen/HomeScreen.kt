package presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.getScreenModel
import cafe.adriel.voyager.navigator.Navigator
import presentation.components.WeatherCard
import ui.theme.surfaceColor


//
// Created by Code For Android on 09/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

class HomeScreen : Screen {

    @Composable
    override fun Content() {
        val viewModel = getScreenModel<HomeScreenViewModel>()

        Box(
            modifier = Modifier.fillMaxSize()

        ) {

            Column(
                modifier = Modifier.fillMaxSize()
                    .background(surfaceColor)
            ) {
                WeatherCard(viewModel.state)

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