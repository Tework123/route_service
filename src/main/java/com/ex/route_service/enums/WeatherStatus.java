package com.ex.route_service.enums;

public enum WeatherStatus {
    // ☁️ Базовые погодные состояния
    CLEAR,                   // Ясно
    CLOUDY,                  // Облачно, без осадков

    // 🌧 Осадки
    RAIN_LIGHT,              // Лёгкий дождь
    RAIN_MODERATE,           // Умеренный дождь
    RAIN_HEAVY,              // Ливень
    SNOW_LIGHT,              // Лёгкий снег
    SNOW_MODERATE,           // Умеренный снегопад
    SNOW_HEAVY,              // Сильный снегопад, метель
    HAIL,                    // Град
    THUNDERSTORM,            // Гроза

    // 🌡 Температура
    COLD_MILD,               // -6 °C до -12 °C
    COLD_SEVERE,             // Ниже -12 °C
    HOT,                     // Выше 30 °C

    // 💨 Ветер
    WIND_MODERATE,           // 8–12 м/с
    WIND_STRONG,             // 12–15 м/с
    WIND_STORM,              // >15 м/с

    // 🔀 Комбинированные статусы (сильно влияют на доставку)
    SNOW_AND_WIND,           // Снег + сильный ветер
    RAIN_AND_WIND,           // Дождь + сильный ветер
    STORM_CONDITIONS,        // Гроза + ветер + осадки

    // ❓ Неизвестно / без данных
    UNKNOWN
}
