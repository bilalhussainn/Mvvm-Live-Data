package com.bilal.mvvmsample.data.db.entity


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

const val CURRENT_WEATHER_ID= 0

@Entity(tableName = "current_weather")
data class CurrentWeatherEntry(
    val cloudcover: Int,
    val feelslike: Double,
    val humidity: Int,
    @SerializedName("is_day")
    val isDay: String,
    @SerializedName("observation_time")
    val observationTime: String,
    val precip: Double,
    val pressure: Double,
    val temperature: Double,
    @SerializedName("uv_index")
    val uvIndex: Int,
    val visibility: Double,
    @SerializedName("weather_code")
    val weatherCode: Int,
    //@SerializedName("weather_descriptions")
    //val weatherDescriptions: String,
 //   @SerializedName("weather_icons")
 //   val weatherIcons: Array<String>,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_speed")
    val windSpeed: Double
){
    @PrimaryKey(autoGenerate = false)
    var id : Int = CURRENT_WEATHER_ID
}