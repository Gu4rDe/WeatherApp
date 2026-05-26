package com.example.weatherapp.presentation.main

/**
 * Запечатанный интерфейс состояния UI главного экрана.
 *
 * - [Loading] — отображается при загрузке данных
 * - [Error] — отображается при ошибке, содержит сообщение для пользователя
 * - [Success] — отображается при успешно загруженной погоде
 */
sealed interface MainUiState {
    /** Идёт загрузка данных. */
    data object Loading : MainUiState

    /**
     * Ошибка при загрузке погоды.
     * @property message Человекочитаемое сообщение об ошибке.
     */
    data class Error(val message: String) : MainUiState

    /**
     * Погода успешно загружена.
     * @property weather Доменная модель с данными о погоде.
     */
    data class Success(val weather: com.example.weatherapp.domain.model.Weather) : MainUiState
}
