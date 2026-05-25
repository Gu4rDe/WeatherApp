package com.example.weatherapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    /**
     * Предоставляет экземпляр [Json] с ignoreUnknownKeys = true,
     * чтобы пропускать неизвестные поля в ответах API без ошибок десериализации.
     *
     * @return Сконфигурированный экземпляр [Json].
     */
    @Provides
    @Singleton
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
    }

    /**
     * Предоставляет Ktor [HttpClient] на движке OkHttp с плагинами:
     * - ContentNegotiation: JSON-сериализация через kotlinx.serialization
     * - HttpTimeout: таймауты 10с (connect), 15с (request/socket)
     * - Logging: полный лог HTTP-запросов/ответов (LogLevel.ALL) для отладки
     *
     * @param json Экземпляр [Json] для плагина ContentNegotiation.
     * @return Сконфигурированный [HttpClient] как singleton.
     */
    @Provides
    @Singleton
    fun provideHttpClient(json: Json): HttpClient = HttpClient(OkHttp) {
        install(ContentNegotiation) {
            json(json)
        }
        install(HttpTimeout) {
            connectTimeoutMillis = 10_000
            requestTimeoutMillis = 15_000
            socketTimeoutMillis = 15_000
        }
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
    }
}
