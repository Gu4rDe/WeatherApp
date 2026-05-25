package com.example.weatherapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.theme.MainCardBackground

@Composable
fun MainCard(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Color.White.copy(alpha = 0.5f),
                shape = RoundedCornerShape(16.dp)
            )
            .background(
                color = MainCardBackground,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        WeatherStatusImage(weatherType = weather.weatherType, size = 96.dp)

        Text(
            text = "${weather.temperature.toInt()} ℃",
            style = MaterialTheme.typography.displayLarge,
            color = Color.White
        )

        Text(
            text = stringResource(id = weather.weatherType.descriptionResId),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White
        )

        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            Text(
                text = stringResource(R.string.max_temp, weather.maxTemperature.toInt()),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
            Text(
                text = stringResource(R.string.min_temp, weather.minTemperature.toInt()),
                style = MaterialTheme.typography.titleMedium,
                color = Color.White
            )
        }
    }
}