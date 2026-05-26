package com.example.weatherapp.di

import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt-модуль для биндинга интерфейса [WeatherRepository] к его реализации [WeatherRepositoryImpl].
 * Устанавливается на уровне SingletonComponent (время жизни приложения).
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    /**
     * Связывает [WeatherRepositoryImpl] как реализацию интерфейса [WeatherRepository].
     *
     * @param impl Реализация репозитория (предоставляется Hilt через @Inject-конструктор).
     * @return Интерфейс [WeatherRepository] для инжекта в ViewModel.
     */
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(
        impl: WeatherRepositoryImpl
    ): WeatherRepository
}
