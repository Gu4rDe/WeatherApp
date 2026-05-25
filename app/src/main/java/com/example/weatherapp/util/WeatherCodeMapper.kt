package com.example.weatherapp.util

import com.example.weatherapp.domain.model.WeatherType

object WeatherCodeMapper {

    fun mapCodeToWeatherType(code: Int): WeatherType {
        return when (code) {
            in 200..232 -> WeatherType.Thunderstorm
            in 300..321 -> WeatherType.ShowerRain
            in 500..504 -> WeatherType.Rain
            511, in 520..531 -> WeatherType.ShowerRain
            in 600..622 -> WeatherType.Snow
            in 701..781 -> WeatherType.Mist
            800 -> WeatherType.ClearSky
            801 -> WeatherType.FewClouds
            802 -> WeatherType.ScatteredClouds
            else -> WeatherType.ScatteredClouds
        }
    }
}