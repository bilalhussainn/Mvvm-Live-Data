package com.bilal.mvvmsample.data.db.unitlocalized

interface UnitSpecificCurrentWeatherEntry {
    val temperature: Double
  //  val conditionText: String
 //   val conditionIconUrl: Array<String>
    val windSpeed: Double
    val windDirection: String
    val precipitationVolume: Double
    val feelsLikeTemperature: Double
    val visibilityDistance: Double
}