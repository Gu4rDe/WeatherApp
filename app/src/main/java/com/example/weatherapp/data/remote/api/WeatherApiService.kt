package com.example.weatherapp.data.remote.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.dto.OpenWeatherResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

class WeatherApiService @Inject constructor(
    private val client: HttpClient
) {
    suspend fun getCurrentWeather(lat: Double, lon: Double): OpenWeatherResponseDto {
        val url = "https://api.openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=${BuildConfig.weatherApiKey}&units=metric"
        return client.get(url).body<OpenWeatherResponseDto>()
    }
}