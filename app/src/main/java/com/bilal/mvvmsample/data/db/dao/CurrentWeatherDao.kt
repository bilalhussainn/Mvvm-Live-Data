package com.bilal.mvvmsample.data.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bilal.mvvmsample.data.db.entity.CURRENT_WEATHER_ID
import com.bilal.mvvmsample.data.db.entity.CurrentWeatherEntry
import com.bilal.mvvmsample.data.db.unitlocalized.ImperrialCurrentWeatherEntry
import com.bilal.mvvmsample.data.db.unitlocalized.MetricCurrentWeatherEntry

@Dao
interface CurrentWeatherDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(weatherEntry: CurrentWeatherEntry)

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherMetric() : LiveData<MetricCurrentWeatherEntry>

    @Query("select * from current_weather where id = $CURRENT_WEATHER_ID")
    fun getWeatherImperial() : LiveData<ImperrialCurrentWeatherEntry>
}