package com.example.weatherapp.util

import com.example.weatherapp.domain.model.WeatherType

/**
 * Маппер строковых значений condition из Yandex Weather API в доменный enum [WeatherType].
 */
object WeatherCodeMapper {

    /**
     * Маппит строковое значение condition из Yandex Weather API
     * в доменный enum [WeatherType].
     *
     * Соответствие условий Yandex → WeatherType:
     * - CLEAR → ClearSky
     * - PARTLY_CLOUDY → FewClouds
     * - CLOUDY, OVERCAST → ScatteredClouds
     * - LIGHT_RAIN, SHOWERS, SLEET → ShowerRain
     * - RAIN, HEAVY_RAIN → Rain
     * - LIGHT_SNOW, SNOW, SNOWFALL → Snow
     * - HAIL, THUNDERSTORM, THUNDERSTORM_WITH_RAIN, THUNDERSTORM_WITH_HAIL → Thunderstorm
     *
     * @param condition Строковое значение condition из ответа Yandex Weather API (например, "CLEAR", "RAIN").
     * @return Соответствующий [WeatherType], или [WeatherType.ScatteredClouds] для неизвестных значений.
     */
    fun mapConditionToWeatherType(condition: String): WeatherType {
        return when (condition) {
            "CLEAR" -> WeatherType.ClearSky
            "PARTLY_CLOUDY" -> WeatherType.FewClouds
            "CLOUDY", "OVERCAST" -> WeatherType.ScatteredClouds
            "LIGHT_RAIN", "SHOWERS", "SLEET" -> WeatherType.ShowerRain
            "RAIN", "HEAVY_RAIN" -> WeatherType.Rain
            "LIGHT_SNOW", "SNOW", "SNOWFALL" -> WeatherType.Snow
            "HAIL", "THUNDERSTORM", "THUNDERSTORM_WITH_RAIN", "THUNDERSTORM_WITH_HAIL" -> WeatherType.Thunderstorm
            else -> WeatherType.ScatteredClouds
        }
    }
}
