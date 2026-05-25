package com.example.weatherapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.theme.MainCardBackground

private data class DetailItem(
    val type: DetailType,
    val weather: Weather
)

private enum class DetailType(
    val labelResId: Int,
    val iconResId: Int
) {
    Wind(R.string.detail_wind, R.drawable.wind_svgrepo_com),
    Humidity(R.string.detail_humidity, R.drawable.humidity_svgrepo_com),
    Visibility(R.string.detail_visibility, R.drawable.visibility_medical_svgrepo_com),
    Pressure(R.string.detail_pressure, R.drawable.pressure_svgrepo_com)
}

@Composable
fun WeatherDetailsCard(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    val details = DetailType.entries.map { DetailItem(it, weather) }

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(details, key = { it.type.name }) { item ->
            DetailCard(type = item.type, weather = item.weather)
        }
    }
}

@Composable
private fun DetailCard(
    type: DetailType,
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(110.dp)
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
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(id = type.iconResId),
                contentDescription = stringResource(id = type.labelResId),
                modifier = Modifier.size(32.dp)
            )
            Text(
                text = stringResource(id = type.labelResId),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Spacer(modifier = Modifier.height(8.dp))

        when (type) {
            DetailType.Wind -> Text(
                text = stringResource(R.string.wind_value, weather.windSpeed),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                maxLines = 1
            )

            DetailType.Humidity -> Text(
                text = stringResource(R.string.humidity_value, weather.humidity),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                maxLines = 1
            )

            DetailType.Visibility -> Text(
                text = stringResource(R.string.visibility_value, weather.visibility / 1000),
                style = MaterialTheme.typography.labelLarge,
                color = Color.White,
                maxLines = 1
            )

            DetailType.Pressure -> {
                val mmHg = (weather.pressure * 0.750064).toInt()
                Text(
                    text = stringResource(R.string.pressure_value, mmHg),
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    maxLines = 1
                )
            }
        }
    }
}