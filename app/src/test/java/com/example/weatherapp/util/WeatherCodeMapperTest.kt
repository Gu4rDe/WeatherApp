package com.example.weatherapp.util

import com.example.weatherapp.domain.model.WeatherType
import org.junit.Assert.assertEquals
import org.junit.Test

class WeatherCodeMapperTest {

    @Test
    fun clearCondition_mapsToClearSky() {
        assertEquals(WeatherType.ClearSky, WeatherCodeMapper.mapConditionToWeatherType("CLEAR"))
    }

    @Test
    fun partlyCloudy_mapsToFewClouds() {
        assertEquals(WeatherType.FewClouds, WeatherCodeMapper.mapConditionToWeatherType("PARTLY_CLOUDY"))
    }

    @Test
    fun cloudyAndOvercast_mapToScatteredClouds() {
        assertEquals(WeatherType.ScatteredClouds, WeatherCodeMapper.mapConditionToWeatherType("CLOUDY"))
        assertEquals(WeatherType.ScatteredClouds, WeatherCodeMapper.mapConditionToWeatherType("OVERCAST"))
    }

    @Test
    fun lightRainShowersSleet_mapToShowerRain() {
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapConditionToWeatherType("LIGHT_RAIN"))
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapConditionToWeatherType("SHOWERS"))
        assertEquals(WeatherType.ShowerRain, WeatherCodeMapper.mapConditionToWeatherType("SLEET"))
    }

    @Test
    fun rainHeavyRain_mapToRain() {
        assertEquals(WeatherType.Rain, WeatherCodeMapper.mapConditionToWeatherType("RAIN"))
        assertEquals(WeatherType.Rain, WeatherCodeMapper.mapConditionToWeatherType("HEAVY_RAIN"))
    }

    @Test
    fun snowConditions_mapToSnow() {
        assertEquals(WeatherType.Snow, WeatherCodeMapper.mapConditionToWeatherType("LIGHT_SNOW"))
        assertEquals(WeatherType.Snow, WeatherCodeMapper.mapConditionToWeatherType("SNOW"))
        assertEquals(WeatherType.Snow, WeatherCodeMapper.mapConditionToWeatherType("SNOWFALL"))
    }

    @Test
    fun thunderstormConditions_mapToThunderstorm() {
        assertEquals(WeatherType.Thunderstorm, WeatherCodeMapper.mapConditionToWeatherType("HAIL"))
        assertEquals(WeatherType.Thunderstorm, WeatherCodeMapper.mapConditionToWeatherType("THUNDERSTORM"))
        assertEquals(WeatherType.Thunderstorm, WeatherCodeMapper.mapConditionToWeatherType("THUNDERSTORM_WITH_RAIN"))
        assertEquals(WeatherType.Thunderstorm, WeatherCodeMapper.mapConditionToWeatherType("THUNDERSTORM_WITH_HAIL"))
    }

    @Test
    fun unknownCondition_defaultsToScatteredClouds() {
        assertEquals(WeatherType.ScatteredClouds, WeatherCodeMapper.mapConditionToWeatherType("UNKNOWN"))
        assertEquals(WeatherType.ScatteredClouds, WeatherCodeMapper.mapConditionToWeatherType(""))
    }
}
