package com.bilal.mvvmsample.data.db.unitlocalized

import androidx.room.ColumnInfo

data class ImperrialCurrentWeatherEntry(
    @ColumnInfo(name = "temperature")
    override val temperature: Double,
    @ColumnInfo(name = "weather_descriptions")
    override val conditionText: String,
    @ColumnInfo(name = "weatherIcons")
    override val conditionIconUrl: String,
    @ColumnInfo(name = "windSpeed")
    override val windSpeed: Double,
    @ColumnInfo(name = "windDir")
    override val windDirection: String,  //
    @ColumnInfo(name = "temperature")
    override val precipitationVolume: Double,
    @ColumnInfo(name = "feelslike")
    override val feelsLikeTemperature: Double,
    @ColumnInfo(name = "visibility")
    override val visibilityDistance: Double
):UnitSpecificCurrentWeatherEntry