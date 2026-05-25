package com.example.weatherapp.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

/**
 * ViewModel главного экрана — управляет состоянием UI через [StateFlow],
 * загружает погоду при инициализации и обрабатывает ошибки сети.
 */
@HiltViewModel
class MainViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    /**
     * Поток состояния UI: [MainUiState.Loading], [MainUiState.Error], [MainUiState.Success].
     * Только для чтения снаружи — запись через внутренний MutableStateFlow.
     */
    private val _uiState = MutableStateFlow<MainUiState>(MainUiState.Loading)
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()

    init {
        loadWeather()
    }

    /**
     * Загружает текущую погоду по заданным координатам.
     * При ошибке сети (таймаут/нет соединения) отображает понятное сообщение.
     *
     * @param lat Широта (по умолчанию Москва — 55.75).
     * @param lon Долгота (по умолчанию Москва — 37.61).
     */
    fun loadWeather(lat: Double = DEFAULT_LAT, lon: Double = DEFAULT_LON) {
        viewModelScope.launch {
            _uiState.value = MainUiState.Loading
            weatherRepository.getCurrentWeather(lat, lon)
                .onSuccess { _uiState.value = MainUiState.Success(it) }
                .onFailure { e ->
                    val message = when (e) {
                        is SocketTimeoutException -> "Превышено время ожидания. Проверьте подключение к интернету."
                        is ConnectException -> "Не удалось подключиться к серверу. Проверьте подключение к интернету."
                        else -> e.message ?: "Неизвестная ошибка"
                    }
                    _uiState.value = MainUiState.Error(message)
                }
        }
    }

    companion object {
        private const val DEFAULT_LAT = 55.75
        private const val DEFAULT_LON = 37.61
    }
}
