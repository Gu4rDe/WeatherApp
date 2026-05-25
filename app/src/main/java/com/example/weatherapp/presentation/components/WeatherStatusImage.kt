package com.example.weatherapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.weatherapp.domain.model.WeatherType

@Composable
fun WeatherStatusImage(
    weatherType: WeatherType,
    size: Dp,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(id = weatherType.iconResId),
        contentDescription = stringResource(id = weatherType.descriptionResId),
        modifier = modifier.size(size)
    )
}