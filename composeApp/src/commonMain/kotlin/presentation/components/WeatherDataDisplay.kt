package presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource


//
// Created by Code For Android on 13/05/24.
// Copyright (c) 2024 CFA. All rights reserved.
//

@OptIn(ExperimentalResourceApi::class)
@Composable
fun WeatherDataDisplay(
    value: Int,
    unit: String,
    icon: DrawableResource,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = TextStyle(),
    iconTint: Color = Color.White
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(resource = icon),
            contentDescription = "",
            tint = iconTint,
            modifier = Modifier.size(25.dp)
        )

        Spacer(modifier = Modifier.width(5.dp))
        Text(text = "$value$unit", style = textStyle)


    }


}