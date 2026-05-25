package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class OpenWeatherResponseDto(
    val main: MainInfoDto,
    val wind: WindInfoDto,
    val visibility: Double,
    @SerialName("weather") val weatherConditions: List<WeatherConditionDto>,
    @SerialName("name") val cityName: String
)

@Serializable
data class MainInfoDto(
    val temp: Double,
    @SerialName("temp_max") val tempMax: Double,
    @SerialName("temp_min") val tempMin: Double,
    val pressure: Double,
    val humidity: Double,
)

@Serializable
data class WindInfoDto(
    val speed: Double
)

@Serializable
data class WeatherConditionDto(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)