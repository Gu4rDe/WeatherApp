package com.example.weatherapp.data.repository

import android.content.Context
import android.location.Geocoder
import com.example.weatherapp.data.remote.api.WeatherApiService
import com.example.weatherapp.domain.model.Weather
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.util.WeatherCodeMapper
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: WeatherApiService,
    @param:ApplicationContext private val context: Context
) : WeatherRepository {

    /**
     * Получает текущую погоду по координатам через Yandex Weather API
     * и маппит ответ в доменную модель [Weather].
     *
     * Название города определяется через обратное геокодирование (Geocoder),
     * так как Yandex API не возвращает cityName. При недоступности Geocoder
     * возвращаются координаты в формате "lat, lon".
     *
     * Минимальная температура берётся из night-части прогноза,
     * максимальная — из day-части. Если прогноз недоступен,
     * используется текущая температура как fallback.
     *
     * @param lat Широта точки наблюдения (от -90.0 до 90.0).
     * @param lon Долгота точки наблюдения (от -180.0 до 180.0).
     * @return [Result.success] с доменной моделью [Weather] при успешном запросе,
     *         [Result.failure] с исключением при ошибке сети или десериализации.
     */
    override suspend fun getCurrentWeather(lat: Double, lon: Double): Result<Weather> {
        return try {
            val dto = apiService.getCurrentWeather(lat, lon)
            val now = dto.data!!.weatherByPoint.now
            val parts = dto.data!!.weatherByPoint.forecast.days.firstOrNull()?.parts

            Result.success(
                Weather(
                    cityName = resolveCityName(lat, lon),
                    temperature = now.temperature.toDouble(),
                    minTemperature = parts?.night?.minTemperature?.toDouble() ?: now.temperature.toDouble(),
                    maxTemperature = parts?.day?.maxTemperature?.toDouble() ?: now.temperature.toDouble(),
                    humidity = now.humidity.toDouble(),
                    pressure = now.pressure.toDouble(),
                    windSpeed = now.windSpeed.toDouble(),
                    visibility = now.visibility.toDouble(),
                    weatherType = WeatherCodeMapper.mapConditionToWeatherType(now.condition)
                )
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Определяет название города по координатам через Android Geocoder.
     *
     * Приоритет извлечения: locality (город) → subLocality (район) → строка координат.
     * Не требует разрешений на геолокацию — координаты уже предоставлены вызывающей стороной.
     *
     * @param lat Широта точки для обратного геокодирования.
     * @param lon Долгота точки для обратного геокодирования.
     * @return Название города, района или строка "lat, lon" при ошибке или отсутствии результатов.
     */
    private fun resolveCityName(lat: Double, lon: Double): String {
        return try {
            val addresses = Geocoder(context, Locale.getDefault()).getFromLocation(lat, lon, 1)
            val address = addresses?.firstOrNull()
            address?.locality
                ?: address?.subLocality
                ?: "%.2f, %.2f".format(lat, lon)
        } catch (e: Exception) {
            "%.2f, %.2f".format(lat, lon)
        }
    }
}
