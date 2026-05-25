package com.example.weatherapp.util

import com.example.weatherapp.domain.model.WeatherType
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherCodeMapperTest {

    @Test
    fun thunderstormCodes_mapCorrectly() {
        assertEquals(WeatherType.Thunderstorm, WeatherCodeMapper.mapCodeToWeatherType(200))
        assertEquals(WeatherType.Thunderstorm, WeatherCodeMapper.mapCodeToWeatherType(210))
        assertEquals(WeatherType.Thunderstorm, WeatherCodeMapper.mapCodeToWeatherType(232))
    }

    @Test
    fun drizzleCodes_mapToShowerRain() {
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapCodeToWeatherType(300))
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapCodeToWeatherType(310))
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapCodeToWeatherType(321))
    }

    @Test
    fun rainCodes_mapCorrectly() {
        assertEquals(WeatherType.Rain, WeatherCodeMapper.mapCodeToWeatherType(500))
        assertEquals(WeatherType.Rain, WeatherCodeMapper.mapCodeToWeatherType(502))
        assertEquals(WeatherType.Rain, WeatherCodeMapper.mapCodeToWeatherType(504))
    }

    @Test
    fun freezingRainAndShowerRain_mapToShowerRain() {
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapCodeToWeatherType(511))
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapCodeToWeatherType(520))
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapCodeToWeatherType(531))
    }

    @Test
    fun snowCodes_mapToSnow() {
        assertEquals(WeatherType.Snow, WeatherCodeMapper.mapCodeToWeatherType(600))
        assertEquals(WeatherType.Snow, WeatherCodeMapper.mapCodeToWeatherType(610))
        assertEquals(WeatherType.Snow, WeatherCodeMapper.mapCodeToWeatherType(622))
    }

    @Test
    fun mistCodes_mapToMist() {
        assertEquals(WeatherType.Mist, WeatherCodeMapper.mapCodeToWeatherType(701))
        assertEquals(WeatherType.Mist, WeatherCodeMapper.mapCodeToWeatherType(750))
        assertEquals(WeatherType.Mist, WeatherCodeMapper.mapCodeToWeatherType(781))
    }

    @Test
    fun clearSky_mapCorrectly() {
        assertEquals(WeatherType.ClearSky, WeatherCodeMapper.mapCodeToWeatherType(800))
    }

    @Test
    fun cloudCodes_mapCorrectly() {
        assertEquals(WeatherType.FewClouds, WeatherCodeMapper.mapCodeToWeatherType(801))
        assertEquals(WeatherType.ScatteredClouds, WeatherCodeMapper.mapCodeToWeatherType(802))
        }

    @Test
    fun unknownCode_defaultsToScatteredClouds() {
        assertEquals(WeatherType.ScatteredClouds, WeatherCodeMapper.mapCodeToWeatherType(999))
        assertEquals(WeatherType.ScatteredClouds, WeatherCodeMapper.mapCodeToWeatherType(-1))
        assertEquals(WeatherType.ScatteredClouds, WeatherCodeMapper.mapCodeToWeatherType(0))
    }
}