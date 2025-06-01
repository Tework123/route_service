package com.ex.route_service.service;

import com.ex.route_service.client.OpenWeatherMapClient;
import com.ex.route_service.dto.OpenWeatherMapDto.OpenWeatherResponseDto;
import com.ex.route_service.dto.RouteServiceDto.locationPointDto.LocationDto;
import com.ex.route_service.enums.WeatherStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapService {
    private final OpenWeatherMapClient openWeatherMapClient;

    public WeatherStatus getWeather(LocationDto locationDto) {
        OpenWeatherResponseDto weatherResponseDto =
                openWeatherMapClient.getWeather(locationDto.getLongitude(), locationDto.getLatitude());

        if (weatherResponseDto == null || weatherResponseDto.getWeather() == null || weatherResponseDto.getWeather().isEmpty()) {
            return WeatherStatus.UNKNOWN;
        }

        OpenWeatherResponseDto.Weather weather = weatherResponseDto.getWeather().getFirst();
        OpenWeatherResponseDto.Main main = weatherResponseDto.getMain();
        OpenWeatherResponseDto.Wind wind = weatherResponseDto.getWind();

        int weatherCode = weather.getId();
        double tempCelsius = main.getTemp();
        double windSpeed = wind != null ? wind.getSpeed() : 0;

        WeatherStatus baseStatus = getWeatherStatus(weatherCode);

        // Температурные уточнения
        boolean isSevereCold = tempCelsius < -12;
        boolean isMildCold = tempCelsius >= -12 && tempCelsius <= -6;
        boolean isHot = tempCelsius > 30;

        // Ветер
        boolean isModerateWind = windSpeed >= 8 && windSpeed < 12;
        boolean isStrongWind = windSpeed >= 12 && windSpeed <= 15;
        boolean isStormWind = windSpeed > 15;

        // Комбинированные условия
        if ((WeatherStatus.SNOW_LIGHT.equals(baseStatus)
                || WeatherStatus.SNOW_MODERATE.equals(baseStatus)
                || WeatherStatus.SNOW_HEAVY.equals(baseStatus))
                && (isStrongWind || isStormWind)) {
            return WeatherStatus.SNOW_AND_WIND;
        }

        if ((WeatherStatus.RAIN_LIGHT.equals(baseStatus)
                || WeatherStatus.RAIN_MODERATE.equals(baseStatus)
                || WeatherStatus.RAIN_HEAVY.equals(baseStatus))
                && (isStrongWind || isStormWind)) {
            return WeatherStatus.RAIN_AND_WIND;
        }

        if (WeatherStatus.THUNDERSTORM.equals(baseStatus)
                && (isModerateWind || isStrongWind || isStormWind)) {
            return WeatherStatus.STORM_CONDITIONS;
        }

        if (isSevereCold) return WeatherStatus.COLD_SEVERE;
        if (isMildCold) return WeatherStatus.COLD_MILD;
        if (isHot) return WeatherStatus.HOT;

        if (isStormWind) return WeatherStatus.WIND_STORM;
        if (isStrongWind) return WeatherStatus.WIND_STRONG;
        if (isModerateWind) return WeatherStatus.WIND_MODERATE;

        return baseStatus;
    }

    private static WeatherStatus getWeatherStatus(int weatherCode) {
        WeatherStatus baseStatus;

        switch (weatherCode) {
            case 800 -> baseStatus = WeatherStatus.CLEAR;
            case 801, 802, 803, 804 -> baseStatus = WeatherStatus.CLOUDY;

            case 500 -> baseStatus = WeatherStatus.RAIN_LIGHT;
            case 501 -> baseStatus = WeatherStatus.RAIN_MODERATE;
            case 502, 503, 504 -> baseStatus = WeatherStatus.RAIN_HEAVY;

            case 600 -> baseStatus = WeatherStatus.SNOW_LIGHT;
            case 601 -> baseStatus = WeatherStatus.SNOW_MODERATE;
            case 602, 622 -> baseStatus = WeatherStatus.SNOW_HEAVY;

            case 906 -> baseStatus = WeatherStatus.HAIL;

            case 200, 201, 202, 210, 211, 212, 221, 230, 231, 232 -> baseStatus = WeatherStatus.THUNDERSTORM;

            default -> baseStatus = WeatherStatus.UNKNOWN;
        }
        return baseStatus;
    }
}
