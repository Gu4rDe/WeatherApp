package com.example.weatherapp.domain.model

import androidx.annotation.DrawableRes
import com.example.weatherapp.R

enum class WeatherType(val descriptionResId: Int, @param:DrawableRes val iconResId: Int) {
    ClearSky(R.string.weather_clear_sky, R.drawable.sun_2_svgrepo_com),
    FewClouds(R.string.weather_few_clouds, R.drawable.partly_sunny_outline_svgrepo_com),
    ScatteredClouds(R.string.weather_scattered_clouds, R.drawable.cloudy_svgrepo_com),
    ShowerRain(R.string.weather_shower_rain, R.drawable.shower_svgrepo_com),
    Rain(R.string.weather_rain, R.drawable.rain_svgrepo_com__1_),
    Thunderstorm(R.string.weather_thunderstorm, R.drawable.storm_svgrepo_com),
    Snow(R.string.weather_snow, R.drawable.snow_cloud_svgrepo_com),
    Mist(R.string.weather_mist, R.drawable.fog_svgrepo_com)
}