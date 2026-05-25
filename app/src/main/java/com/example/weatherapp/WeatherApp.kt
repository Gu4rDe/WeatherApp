package com.example.weatherapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Точка входа приложения. Аннотация [HiltAndroidApp] инициализирует
 * граф зависимостей Hilt на уровне Application.
 */
@HiltAndroidApp
class WeatherApp : Application()
