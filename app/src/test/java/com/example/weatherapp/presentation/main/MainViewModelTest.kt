package com.example.weatherapp.presentation.main

import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.model.WeatherType
import com.example.weatherapp.domain.repository.WeatherRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MainViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val weatherRepository: WeatherRepository = mockk()

    private val testWeather = Weather(
        cityName = "Moscow",
        temperature = 25.5,
        minTemperature = 20.0,
        maxTemperature = 28.0,
        humidity = 65.0,
        pressure = 1013.0,
        windSpeed = 5.2,
        visibility = 10000.0,
        weatherType = WeatherType.ClearSky
    )

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialState_isLoading() {
        coEvery { weatherRepository.getCurrentWeather(any(), any()) } returns Result.success(testWeather)

        val viewModel = MainViewModel(weatherRepository)

        assertTrue(viewModel.uiState.value is MainUiState.Loading)
    }

    @Test
    fun loadWeather_success_updatesStateToSuccess() = runTest {
        coEvery { weatherRepository.getCurrentWeather(55.75, 37.61) } returns Result.success(testWeather)

        val viewModel = MainViewModel(weatherRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is MainUiState.Success)
        val weather = (state as MainUiState.Success).weather
        assertEquals("Moscow", weather.cityName)
        assertEquals(25.5, weather.temperature, 0.01)
        assertEquals(WeatherType.ClearSky, weather.weatherType)
    }

    @Test
    fun loadWeather_failure_updatesStateToError() = runTest {
        coEvery { weatherRepository.getCurrentWeather(any(), any()) } returns Result.failure(RuntimeException("Network error"))

        val viewModel = MainViewModel(weatherRepository)
        testDispatcher.scheduler.advanceUntilIdle()

        val state = viewModel.uiState.value
        assertTrue(state is MainUiState.Error)
        assertEquals("Network error", (state as MainUiState.Error).message)
    }

    @Test
    fun loadWeather_retryAfterError_showsSuccess() = runTest {
        coEvery { weatherRepository.getCurrentWeather(any(), any()) } returns Result.failure(RuntimeException("Error"))

        val viewModel = MainViewModel(weatherRepository)
        testDispatcher.scheduler.advanceUntilIdle()
        assertTrue(viewModel.uiState.value is MainUiState.Error)

        coEvery { weatherRepository.getCurrentWeather(any(), any()) } returns Result.success(testWeather)
        viewModel.loadWeather()
        testDispatcher.scheduler.advanceUntilIdle()

        assertTrue(viewModel.uiState.value is MainUiState.Success)
    }
}