package com.example.weatherapp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.presentation.components.MainCard
import com.example.weatherapp.presentation.components.WeatherDetailsCard
import com.example.weatherapp.presentation.theme.MainCardBackground

/**
 * Главный экран приложения — отображает погоду согласно [MainUiState]:
 * индикатор загрузки, сообщение об ошибке с кнопкой повтора, или данные погоды.
 *
 * @param viewModel ViewModel главного экрана (HiltViewModel).
 * @param modifier Модификатор для корневого контейнера.
 */
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()

    when (val state = uiState) {
        is MainUiState.Loading -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MainCardBackground),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator()
            }
        }

        is MainUiState.Error -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .background(MainCardBackground)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = state.message)
                Spacer(Modifier.height(16.dp))
                Button(onClick = { viewModel.loadWeather() }) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }

        is MainUiState.Success -> {
            WeatherContent(weather = state.weather, modifier = modifier)
        }
    }
}

/**
 * Содержимое экрана при успешно загруженной погоде:
 * главная карточка с температурой + детальные показатели.
 *
 * @param weather Загруженные данные о погоде.
 * @param modifier Модификатор для контейнера.
 */
@Composable
private fun WeatherContent(
    weather: Weather,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MainCardBackground),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainCard(weather = weather)

        Spacer(Modifier.height(16.dp))

        WeatherDetailsCard(
            weather = weather,
            modifier = Modifier
        )
    }
}
