package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.model.WeatherType
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.util.WeatherCodeMapper
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService
) : WeatherRepository {

    override suspend fun getCurrentWeather(lat: Double, lon: Double): Result<Weather> {
        return try {
            val dto = apiService.getCurrentWeather(lat, lon)
            val weatherType = dto.weatherConditions.firstOrNull()?.id?.let {
                WeatherCodeMapper.mapCodeToWeatherType(it)
            } ?: WeatherType.ScatteredClouds

            Result.success(
                Weather(
                    cityName = dto.cityName,
                    temperature = dto.main.temp,
                    minTemperature = dto.main.tempMin,
                    maxTemperature = dto.main.tempMax,
                    humidity = dto.main.humidity,
                    pressure = dto.main.pressure,
                    windSpeed = dto.wind.speed,
                    visibility = dto.visibility,
                    weatherType = weatherType
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}