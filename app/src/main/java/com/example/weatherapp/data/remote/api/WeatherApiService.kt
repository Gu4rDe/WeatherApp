package com.example.weatherapp.data.remote.api

import com.example.weatherapp.BuildConfig
import com.example.weatherapp.data.remote.dto.YandexWeatherResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import java.util.Locale
import javax.inject.Inject

class WeatherApiService @Inject constructor(
    private val client: HttpClient
) {
    /**
     * Выполняет GraphQL-запрос к Yandex Weather API v3 для получения
     * текущей погоды и прогноза на ближайший день по заданным координатам.
     *
     * Координаты передаются через GraphQL variables с принудительной Locale.US,
     * чтобы избежать форматирования с запятой вместо точки (например, "55,75" вместо "55.75").
     *
     * Поля feelsLike и isThunder недоступны на базовом тарифе и не запрашиваются.
     * Давление возвращается в мм рт.ст., иконки — в формате SVG.
     *
     * @param lat Широта точки наблюдения (от -90.0 до 90.0).
     * @param lon Долгота точки наблюдения (от -180.0 до 180.0).
     * @return [YandexWeatherResponseDto] с данными о погоде (гарантированно data != null).
     * @throws IllegalStateException если API вернул ошибку (data == null), сообщение берётся из errors[0].message.
     */
    suspend fun getCurrentWeather(lat: Double, lon: Double): YandexWeatherResponseDto {
        val query = """query(${'$'}lat: Float!, ${'$'}lon: Float!) { weatherByPoint(request: { lat: ${'$'}lat, lon: ${'$'}lon }) { now { temperature humidity pressure windSpeed visibility condition cloudiness icon(format: SVG) } forecast { days(limit: 1) { parts { night { minTemperature } day { maxTemperature } } } } } }"""

        val variables = """{"lat":${"%.4f".format(Locale.US, lat)},"lon":${"%.4f".format(Locale.US, lon)}}"""

        val response = client.post("https://api.weather.yandex.ru/graphql/query") {
            contentType(ContentType.Application.Json)
            header("X-Yandex-Weather-Key", BuildConfig.weatherApiKey)
            setBody("""{"query":"$query","variables":$variables}""")
        }.body<YandexWeatherResponseDto>()

        if (response.data == null) {
            val message = response.errors?.firstOrNull()?.message ?: "Unknown API error"
            throw IllegalStateException(message)
        }

        return response
    }
}
