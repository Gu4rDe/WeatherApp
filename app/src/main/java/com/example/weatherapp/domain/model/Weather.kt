package com.example.weatherapp.domain.model

data class Weather(
    val cityName: String,
    val temperature: Double,
    val minTemperature: Double,
    val maxTemperature: Double,
    val humidity: Double,
    val pressure: Double,
    val windSpeed: Double,
    val visibility: Double,
    val weatherType: WeatherType
)