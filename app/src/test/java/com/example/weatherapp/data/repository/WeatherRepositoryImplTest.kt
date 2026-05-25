package com.example.weatherapp.data.repository

import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.data.remote.dto.MainInfoDto
import com.example.weatherapp.data.remote.dto.OpenWeatherResponseDto
import com.example.weatherapp.data.remote.dto.WeatherConditionDto
import com.example.weatherapp.data.remote.dto.WindInfoDto
import com.example.weatherapp.domain.model.WeatherType
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherRepositoryImplTest {

    private val apiService: WeatherApiService = mockk()
    private val repository = WeatherRepositoryImpl(apiService)

    private val testDto = OpenWeatherResponseDto(
        main = MainInfoDto(
            temp = 25.5,
            tempMax = 28.0,
            tempMin = 20.0,
            pressure = 1013.0,
            humidity = 65.0
        ),
        wind = WindInfoDto(speed = 5.2),
        visibility = 10000.0,
        weatherConditions = listOf(
            WeatherConditionDto(id = 800, main = "Clear", description = "clear sky", icon = "01d")
        ),
        cityName = "Moscow"
    )

    @Test
    fun getCurrentWeather_success_returnsWeatherWithCorrectFields() = runTest {
        coEvery { apiService.getCurrentWeather(55.75, 37.61) } returns testDto

        val result = repository.getCurrentWeather(55.75, 37.61)

        assertTrue(result.isSuccess)
        val weather = result.getOrThrow()
        assertEquals("Moscow", weather.cityName)
        assertEquals(25.5, weather.temperature, 0.01)
        assertEquals(28.0, weather.maxTemperature, 0.01)
        assertEquals(20.0, weather.minTemperature, 0.01)
        assertEquals(65.0, weather.humidity, 0.01)
        assertEquals(1013.0, weather.pressure, 0.01)
        assertEquals(5.2, weather.windSpeed, 0.01)
        assertEquals(10000.0, weather.visibility, 0.01)
        assertEquals(WeatherType.ClearSky, weather.weatherType)
    }

    @Test
    fun getCurrentWeather_thunderstormCode_mapsCorrectly() = runTest {
        val dto = testDto.copy(
            weatherConditions = listOf(
                WeatherConditionDto(id = 200, main = "Thunderstorm", description = "thunderstorm", icon = "11d")
            )
        )
        coEvery { apiService.getCurrentWeather(any(), any()) } returns dto

        val result = repository.getCurrentWeather(0.0, 0.0)

        assertTrue(result.isSuccess)
        assertEquals(WeatherType.Thunderstorm, result.getOrThrow().weatherType)
    }

    @Test
    fun getCurrentWeather_emptyWeatherConditions_defaultsToScatteredClouds() = runTest {
        val dto = testDto.copy(weatherConditions = emptyList())
        coEvery { apiService.getCurrentWeather(any(), any()) } returns dto

        val result = repository.getCurrentWeather(0.0, 0.0)

        assertTrue(result.isSuccess)
        assertEquals(WeatherType.ScatteredClouds, result.getOrThrow().weatherType)
    }

    @Test
    fun getCurrentWeather_apiException_returnsFailure() = runTest {
        coEvery { apiService.getCurrentWeather(any(), any()) } throws RuntimeException("Network error")

        val result = repository.getCurrentWeather(55.75, 37.61)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}