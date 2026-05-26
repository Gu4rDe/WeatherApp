# ☁️ WeatherApp

Приложение погоды для Android, написанное на **Kotlin** с использованием **Jetpack Compose**. Отображает текущую погоду для выбранного города через **Yandex Weather API v3 (GraphQL)**.

## 📋 Возможности

- Текущая температура, мин/макс, описание погодных условий
- Детальная информация: скорость ветра, влажность, видимость, давление (мм рт.ст.)
- Иконки погодных условий (ясно, облачно, дождь, гроза, снег, туман)
- Обработка ошибок сети с возможностью повтора
- Кастомный пиксельный шрифт Minecraft
- Обратное геокодирование (Geocoder) для определения города по координатам

## 🛠 Стек технологий

| Категория | Библиотеки |
|-----------|------------|
| **API** | Yandex Weather API v3 (GraphQL) |
| **UI** | Jetpack Compose, Material Design 3 |
| **Архитектура** | MVVM + Clean Architecture |
| **DI** | Dagger Hilt |
| **Сеть** | Ktor Client (OkHttp) + Kotlinx Serialization |
| **Асинхронность** | Kotlin Coroutines + StateFlow |
| **Геокодирование** | Android Geocoder |
| **Тестирование** | JUnit 4, MockK |

## 🚀 Сборка и запуск

1. Клонируйте репозиторий
2. Добавьте API-ключ в `local.properties`:
   ```properties
   weatherApiKey=ВАШ_API_КЛЮЧ_ЯНДЕКС_ПОГОДЫ
   ```
3. Откройте проект в Android Studio и запустите на устройстве или эмуляторе

> **Примечание:** используется **базовый тариф** Yandex Weather API v3 — поля `feelsLike` и `isThunder` недоступны. Графические иконки отрисовываются локально из ассетов (SVG-репозитории), иконка из API не используется для отображения.

## 📂 Структура проекта

```
app/src/main/java/com/example/weatherapp/
├── di/                    # Hilt-модули (сеть, репозиторий)
├── data/
│   ├── remote/
│   │   ├── api/           # Ktor API-сервис (GraphQL)
│   │   └── dto/           # DTO-модели Yandex Weather
│   └── repository/        # Реализация репозитория
├── domain/
│   ├── model/             # Доменные модели (Weather, WeatherType)
│   └── repository/        # Интерфейс репозитория
├── presentation/
│   ├── components/        # UI-компоненты (MainCard, WeatherDetailsCard)
│   ├── main/              # Главный экран + ViewModel + UiState
│   └── theme/             # Тема (цвета, шрифты)
└── util/                  # Утилиты (маппинг condition → WeatherType)
```

## 🏗 Архитектура

Приложение построено по **Clean Architecture** с паттерном **MVVM**:

```
Yandex Weather API  ←(GraphQL/Ktor)→  WeatherApiService  →  WeatherRepositoryImpl
                                                                     ↓
                                                              WeatherRepository
                                                                     ↓
                                                               MainViewModel
                                                                (StateFlow)
                                                                     ↓
                                                          MainScreen (Compose)
```

- **Data** — GraphQL-запросы к Yandex API, десериализация JSON, маппинг DTO → Domain
- **Domain** — бизнес-модели (`Weather`, `WeatherType`) и интерфейс `WeatherRepository`
- **Presentation** — `MainViewModel` на `StateFlow` + Jetpack Compose UI
- **DI** — `NetworkModule` (Ktor, JSON), `RepositoryModule` (биндинг интерфейса)

### Особенности реализации

- **GraphQL variables** с `Locale.US` для корректного форматирования координат (точка вместо запятой)
- **Geocoder** для получения названия города — Yandex API не возвращает `cityName`
- **Давление** — API отдаёт в мм рт.ст., доменная модель хранит как есть
- **min/max температура** — из прогноза: `night.minTemperature` и `day.maxTemperature`
- **Логирование** — Ktor Logging с `LogLevel.ALL` для отладки в Logcat

## 🧪 Тестирование

```bash
./gradlew test
```

Unit-тесты написаны с использованием **JUnit 4** и **MockK**.

## 📄 Лицензия

MIT
