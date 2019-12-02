package com.bilal.mvvmsample.data.reposiitory

import androidx.lifecycle.LiveData
import com.bilal.mvvmsample.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry

interface ForecastRepository {
    //Asynchronous in caroutine
    suspend fun getCurrentWeather(metric: Boolean) : LiveData<out UnitSpecificCurrentWeatherEntry> //out means class which only implementing UnitSpecificCurrentWeatherEntry not UnitSpecificCurrentWeatherEntry

}