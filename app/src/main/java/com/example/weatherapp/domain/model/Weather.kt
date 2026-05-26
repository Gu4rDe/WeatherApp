package com.example.weatherapp.domain.model

/**
 * Доменная модель погоды — агностична к конкретному API.
 * Единицы измерения: температура — °C, влажность — %, давление — мм рт.ст.,
 * скорость ветра — м/с, видимость — метры.
 *
 * @property cityName Название города (из Geocoder, не из API).
 * @property temperature Текущая температура в °C.
 * @property minTemperature Минимальная температура на сегодня в °C (из ночного прогноза).
 * @property maxTemperature Максимальная температура на сегодня в °C (из дневного прогноза).
 * @property humidity Относительная влажность в %.
 * @property pressure Атмосферное давление в мм рт.ст.
 * @property windSpeed Скорость ветра в м/с.
 * @property visibility Дальность видимости в метрах.
 * @property weatherType Тип погодных условий (ясно, дождь, гроза и т.д.).
 */
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
