package com.example.weatherapp.domain.repository

import com.example.weatherapp.domain.model.Weather

/**
 * Репозиторий погоды — точка входа для получения погодных данных.
 * Абстрагирует слой данных от презентации.
 */
interface WeatherRepository {
    /**
     * Запрашивает текущую погоду и прогноз по координатам.
     *
     * @param lat Широта точки наблюдения (от -90.0 до 90.0).
     * @param lon Долгота точки наблюдения (от -180.0 до 180.0).
     * @return [Result.success] с [Weather] при успехе, [Result.failure] при ошибке сети/парсинга.
     */
    suspend fun getCurrentWeather(lat: Double, lon: Double): Result<Weather>
}
