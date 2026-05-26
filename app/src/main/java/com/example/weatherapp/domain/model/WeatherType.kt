package com.example.weatherapp.domain.model

import androidx.annotation.DrawableRes
import com.example.weatherapp.R

/**
 * Типы погодных условий с привязкой к строковому ресурсу описания
 * и drawable-иконке для отображения в UI.
 *
 * @property descriptionResId Строковый ресурс с текстовым описанием (например, "Ясно").
 * @property iconResId Drawable-ресурс иконки погодного условия.
 */
enum class WeatherType(val descriptionResId: Int, @param:DrawableRes val iconResId: Int) {
    /** Ясное небо. */
    ClearSky(R.string.weather_clear_sky, R.drawable.sun_2_svgrepo_com),
    /** Малооблачно. */
    FewClouds(R.string.weather_few_clouds, R.drawable.partly_sunny_outline_svgrepo_com),
    /** Облачно / пасмурно. */
    ScatteredClouds(R.string.weather_scattered_clouds, R.drawable.cloudy_svgrepo_com),
    /** Небольшой дождь / моросящий дождь / мокрый снег. */
    ShowerRain(R.string.weather_shower_rain, R.drawable.shower_svgrepo_com),
    /** Дождь / сильный дождь. */
    Rain(R.string.weather_rain, R.drawable.rain_svgrepo_com__1_),
    /** Гроза (включая град и грозу с дождём). */
     Thunderstorm(R.string.weather_thunderstorm, R.drawable.storm_svgrepo_com),
    /** Снег (включая снегопад). */
    Snow(R.string.weather_snow, R.drawable.snow_cloud_svgrepo_com),
}
