package com.example.weatherapp.presentation.main

sealed interface MainUiState {
    data object Loading : MainUiState
    data class Error(val message: String) : MainUiState
    data class Success(val weather: com.example.weatherapp.domain.model.Weather) : MainUiState
}