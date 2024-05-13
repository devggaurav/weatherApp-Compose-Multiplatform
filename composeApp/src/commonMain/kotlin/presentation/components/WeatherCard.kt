package presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.LocalDateTime
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.theme.primaryColor
import ui.theme.primaryContainerDark
import ui.theme.surfaceColor
import ui.theme.textColor
import weatherappcmm.composeapp.generated.resources.Res
import weatherappcmm.composeapp.generated.resources.ic_drop
import weatherappcmm.composeapp.generated.resources.ic_pressure
import weatherappcmm.composeapp.generated.resources.ic_wind
import kotlin.math.roundToInt


//
// Created by Code For Android on 13/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WeatherCard(state: WeatherState) {

    state.weatherInfo?.currentWeatherData?.let { data ->

        Card(
            colors = CardDefaults.cardColors(
                containerColor = primaryContainerDark,
                contentColor = textColor
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.padding(16.dp)


        ) {
            Column(
                modifier = Modifier.fillMaxWidth().padding(
                    16.dp
                ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val hour = if (data.time.hour < 10) {
                    "0${data.time.hour}"
                } else {
                    data.time.hour.toString()
                }
                val minute = if (data.time.minute < 10) {
                    "0${data.time.minute}"
                } else {
                    data.time.minute.toString()
                }

                Text(
                    text = "Today ${hour}:${minute}",
                    modifier = Modifier.align(Alignment.End),
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Image(
                    painter = painterResource(resource = data.weatherType.iconRes),
                    contentDescription = "",
                    modifier = Modifier.width(200.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "${data.temperatureCelsius}°C",
                    fontSize = 50.sp,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = data.weatherType.weatherDesc, fontSize = 20.sp, color = Color.White)
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {

                    WeatherDataDisplay(
                        value = data.pressure.roundToInt(),
                        unit = "hpa",
                        icon = Res.drawable.ic_pressure,
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.humidity.roundToInt(),
                        unit = "%",
                        icon = Res.drawable.ic_drop,
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )
                    WeatherDataDisplay(
                        value = data.windSpeed.roundToInt(),
                        unit = "km/h",
                        icon = Res.drawable.ic_wind,
                        iconTint = Color.White,
                        textStyle = TextStyle(color = Color.White)
                    )

                }


            }

        }


    }


}