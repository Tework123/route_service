package com.ex.route_service.enums;

public enum WeatherStatus {

    /** ☁️ Ясно */
    CLEAR,

    /** ☁️ Облачно, без осадков */
    CLOUDY,

    /** 🌧 Лёгкий дождь */
    RAIN_LIGHT,

    /** 🌧 Умеренный дождь */
    RAIN_MODERATE,

    /** 🌧 Ливень */
    RAIN_HEAVY,

    /** 🌧 Лёгкий снег */
    SNOW_LIGHT,

    /** 🌧 Умеренный снегопад */
    SNOW_MODERATE,

    /** 🌧 Сильный снегопад, метель */
    SNOW_HEAVY,

    /** 🌧 Град */
    HAIL,

    /** 🌧 Гроза */
    THUNDERSTORM,

    /** 🌡 -6 °C до -12 °C */
    COLD_MILD,

    /** 🌡 Ниже -12 °C */
    COLD_SEVERE,

    /** 🌡 Выше 30 °C */
    HOT,

    /** 💨 Ветер 8–12 м/с */
    WIND_MODERATE,

    /** 💨 Ветер 12–15 м/с */
    WIND_STRONG,

    /** 💨 Ветер >15 м/с */
    WIND_STORM,

    /** 🔀 Снег + сильный ветер */
    SNOW_AND_WIND,

    /** 🔀 Дождь + сильный ветер */
    RAIN_AND_WIND,

    /** 🔀 Гроза + ветер + осадки */
    STORM_CONDITIONS,

    /** ❓ Неизвестно / без данных */
    UNKNOWN
}
