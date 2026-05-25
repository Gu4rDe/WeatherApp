package com.example.weatherapp.data.repository

import android.content.Context
import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.data.remote.dto.DaypartDto
import com.example.weatherapp.data.remote.dto.DaypartsDto
import com.example.weatherapp.data.remote.dto.ForecastDayDto
import com.example.weatherapp.data.remote.dto.ForecastDto
import com.example.weatherapp.data.remote.dto.NowDto
import com.example.weatherapp.data.remote.dto.WeatherByPointDto
import com.example.weatherapp.data.remote.dto.WeatherByPointWrapper
import com.example.weatherapp.data.remote.dto.YandexWeatherResponseDto
import com.example.weatherapp.domain.model.WeatherType
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class WeatherRepositoryImplTest {

    private val apiService: WeatherApiService = mockk()
    private val context: Context = mockk()
    private val repository = WeatherRepositoryImpl(apiService, context)

    private val testDto = YandexWeatherResponseDto(
        data = WeatherByPointWrapper(
            weatherByPoint = WeatherByPointDto(
                now = NowDto(
                    temperature = 25,
                    humidity = 65,
                    pressure = 1013,
                    windSpeed = 5.2f,
                    visibility = 10000,
                    condition = "CLEAR",
                    cloudiness = "CLEAR",
                    icon = "skc_d"
                ),
                forecast = ForecastDto(
                    days = listOf(
                        ForecastDayDto(
                            parts = DaypartsDto(
                                night = DaypartDto(minTemperature = 20, maxTemperature = 22),
                                day = DaypartDto(minTemperature = 24, maxTemperature = 28)
                            )
                        )
                    )
                )
            )
        )
    )

    @Test
    fun getCurrentWeather_success_returnsWeatherWithCorrectFields() = runTest {
        coEvery { apiService.getCurrentWeather(55.75, 37.61) } returns testDto
        every { context.getSystemService(any()) } returns mockk<android.location.Geocoder>(relaxed = true)

        val result = repository.getCurrentWeather(55.75, 37.61)

        assertTrue(result.isSuccess)
        val weather = result.getOrThrow()
        assertEquals(25.0, weather.temperature, 0.01)
        assertEquals(28.0, weather.maxTemperature, 0.01)
        assertEquals(20.0, weather.minTemperature, 0.01)
        assertEquals(65.0, weather.humidity, 0.01)
        assertEquals(1013.0, weather.pressure, 0.01)
        assertEquals(5.2, weather.windSpeed, 0.01)
        assertEquals(10000.0, weather.visibility, 0.01)
        assertEquals(WeatherType.ClearSky, weather.weatherType)
    }

    @Test
    fun getCurrentWeather_thunderstormCondition_mapsCorrectly() = runTest {
        val dto = YandexWeatherResponseDto(
            data = WeatherByPointWrapper(
                weatherByPoint = WeatherByPointDto(
                    now = testDto.data!!.weatherByPoint.now.copy(condition = "THUNDERSTORM"),
                    forecast = testDto.data!!.weatherByPoint.forecast
                )
            )
        )
        coEvery { apiService.getCurrentWeather(any(), any()) } returns dto
        every { context.getSystemService(any()) } returns mockk<android.location.Geocoder>(relaxed = true)

        val result = repository.getCurrentWeather(0.0, 0.0)

        assertTrue(result.isSuccess)
        assertEquals(WeatherType.Thunderstorm, result.getOrThrow().weatherType)
    }

    @Test
    fun getCurrentWeather_apiException_returnsFailure() = runTest {
        coEvery { apiService.getCurrentWeather(any(), any()) } throws RuntimeException("Network error")

        val result = repository.getCurrentWeather(55.75, 37.61)

        assertTrue(result.isFailure)
        assertEquals("Network error", result.exceptionOrNull()?.message)
    }
}
