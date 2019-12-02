package com.bilal.mvvmsample.data.reposiitory

import androidx.lifecycle.LiveData
import com.bilal.mvvmsample.data.db.dao.CurrentWeatherDao
import com.bilal.mvvmsample.data.db.unitlocalized.UnitSpecificCurrentWeatherEntry
import com.bilal.mvvmsample.data.network.WeatherNetworkDataSource
import com.bilal.mvvmsample.data.network.response.CurrentWeatherResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime
import java.util.*

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val weatherNetworkDataSource: WeatherNetworkDataSource
) : ForecastRepository {

    init {
        weatherNetworkDataSource.downloadedCurrentWeather.observeForever {newCurrentWeather->
            persistFetchedCurrentWeather(newCurrentWeather)

        } //lifecycle doesn't play role on repository.
    }

    override suspend fun getCurrentWeather(metric: Boolean): LiveData<out UnitSpecificCurrentWeatherEntry> {  //out means class which only implementing UnitSpecificCurrentWeatherEntry not UnitSpecificCurrentWeatherEntry
        //withContext returns a value, where as launch doesn't return values.
        return withContext(Dispatchers.IO){
            return@withContext if(metric) currentWeatherDao.getWeatherMetric()
            else currentWeatherDao.getWeatherImperial()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather : CurrentWeatherResponse){
        //In repository we can use GLobal scope. but in activity we should avoid using it, because it has lifecycle.
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.currentWeatherEntry)
        }
    }

    private suspend fun initWeatherData(){

        if(isFetchCurrentNeeded(ZonedDateTime.now().minusHours(1)))
            fetchCurrentWeather()

    }

    private suspend fun fetchCurrentWeather(){
        weatherNetworkDataSource.fetchCurrentWeather(
            "Los Angelas",
            Locale.getDefault().language
        )
    }

    private fun isFetchCurrentNeeded(lastFetchTime : ZonedDateTime) :Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }

}