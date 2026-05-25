package com.example.weatherapp.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Корневой DTO ответа Yandex Weather GraphQL API.
 * [data] — null при ошибке запроса, [errors] — список ошибок GraphQL.
 *
 * @property data Обёртка с данными о погоде, null если API вернул ошибку.
 * @property errors Список ошибок GraphQL-запроса, null если запрос успешен.
 */
@Serializable
data class YandexWeatherResponseDto(
    val data: WeatherByPointWrapper? = null,
    val errors: List<GraphQLErrorDto>? = null
)

/**
 * DTO ошибки GraphQL-запроса.
 *
 * @property message Человекочитаемое описание ошибки.
 * @property extensions Дополнительные данные об ошибке (код, детали).
 */
@Serializable
data class GraphQLErrorDto(
    val message: String,
    val extensions: GraphQLErrorExtensionsDto? = null
)

/**
 * DTO расширений ошибки GraphQL.
 *
 * @property code Код ошибки API (например, "VALIDATION_FAILED").
 */
@Serializable
data class GraphQLErrorExtensionsDto(
    val code: String? = null
)

/**
 * Обёртка вокруг данных weatherByPoint.
 *
 * @property weatherByPoint Данные о погоде по точке (текущие + прогноз).
 */
@Serializable
data class WeatherByPointWrapper(
    val weatherByPoint: WeatherByPointDto
)

/**
 * DTO текущей погоды и прогноза по географической точке.
 *
 * @property now Текущие погодные условия.
 * @property forecast Прогноз на ближайшие дни.
 */
@Serializable
data class WeatherByPointDto(
    val now: NowDto,
    val forecast: ForecastDto
)

/**
 * DTO текущих погодных условий.
 * Давление — в мм рт.ст., видимость — в метрах (0–10000),
 * windSpeed — в м/с, humidity — в процентах.
 *
 * @property temperature Температура воздуха в °C.
 * @property humidity Относительная влажность в %.
 * @property pressure Атмосферное давление в мм рт.ст.
 * @property windSpeed Скорость ветра в м/с.
 * @property visibility Дальность видимости в метрах (0–10000).
 * @property condition Погодное условие (enum Yandex: CLEAR, RAIN и т.д.).
 * @property cloudiness Облачность (enum Yandex: CLEAR, PARTLY и т.д.).
 * @property icon URL иконки погоды в формате SVG.
 */
@Serializable
data class NowDto(
    val temperature: Int,
    val humidity: Int,
    val pressure: Int,
    val windSpeed: Float,
    val visibility: Int,
    val condition: String,
    val cloudiness: String,
    val icon: String
)

/**
 * DTO прогноза погоды.
 *
 * @property days Список дней прогноза.
 */
@Serializable
data class ForecastDto(
    val days: List<ForecastDayDto>
)

/**
 * DTO дня прогноза с частями суток.
 *
 * @property parts Части суток (ночь/утро/день/вечер).
 */
@Serializable
data class ForecastDayDto(
    val parts: DaypartsDto
)

/**
 * DTO частей суток.
 *
 * @property night Ночные погодные данные.
 * @property day Дневные погодные данные.
 */
@Serializable
data class DaypartsDto(
    val night: DaypartDto,
    val day: DaypartDto
)

/**
 * DTO температурных данных части суток.
 * Поля nullable: night содержит только minTemperature, day — только maxTemperature.
 *
 * @property minTemperature Минимальная температура в °C, null если отсутствует.
 * @property maxTemperature Максимальная температура в °C, null если отсутствует.
 */
@Serializable
data class DaypartDto(
    val minTemperature: Int? = null,
    val maxTemperature: Int? = null
)
