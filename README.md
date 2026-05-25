# ☁️ WeatherApp

Приложение погоды для Android, написанное на **Kotlin** с использованием **Jetpack Compose**. Отображает текущую погоду для выбранного города.

## 📋 Возможности

- Текущая температура, мин/макс, описание погодных условий
- Детальная информация: скорость ветра, влажность, видимость, давление
- Иконки погодных условий (ясно, облачно, дождь, гроза, снег, туман)
- Обработка ошибок сети с возможностью повтора
- Кастомный пиксельный шрифт Minecraft

## 🛠 Стек технологий

| Категория | Библиотеки |
|-----------|------------|
| **UI** | Jetpack Compose, Material Design 3 |
| **Архитектура** | MVVM + Clean Architecture |
| **DI** | Dagger Hilt |
| **Сеть** | Ktor Client + Kotlinx Serialization |
| **Асинхронность** | Kotlin Coroutines |
| **Тестирование** | JUnit 4, MockK |

## 🚀 Сборка и запуск

1. Клонируйте репозиторий
2. Добавьте API-ключ в `local.properties`:
   ```properties
   weatherApiKey=ВАШ_API_КЛЮЧ
   ```
3. Откройте проект в Android Studio и запустите на устройстве или эмуляторе

> **Примечание:** в качестве API используется **OpenWeatherMap** — временное решение. В дальнейшем возможна замена провайдера.

## 📂 Структура проекта

```
app/src/main/java/com/example/weatherapp/
├── di/                    # Hilt-модули
├── data/
│   ├── remote/
│   │   ├── api/           # Ktor API-сервис
│   │   └── dto/           # DTO-модели
│   └── repository/        # Реализация репозитория
├── domain/
│   ├── model/             # Доменные модели
│   └── repository/        # Интерфейс репозитория
├── presentation/
│   ├── components/        # UI-компоненты
│   ├── main/              # Главный экран + ViewModel
│   └── theme/             # Тема (цвета, шрифты)
└── util/                  # Утилиты (маппинг кодов погоды)
```

## 🏗 Архитектура

Приложение построено по **Clean Architecture** с паттерном **MVVM**:

```
OpenWeatherMap API  ←(Ktor)→  WeatherApiService  →  WeatherRepositoryImpl
                                                           ↓
                                                    WeatherRepository
                                                           ↓
                                                     MainViewModel
                                                      (StateFlow)
                                                           ↓
                                                MainScreen (Compose)
```

- **Data** — работа с API и маппинг DTO → Domain
- **Domain** — бизнес-модели и интерфейс репозитория
- **Presentation** — ViewModel + Jetpack Compose UI

## 🧪 Тестирование

```bash
./gradlew test
```

Unit-тесты написаны с использованием **JUnit 4** и **MockK**.

## 📄 Лицензия

MIT
