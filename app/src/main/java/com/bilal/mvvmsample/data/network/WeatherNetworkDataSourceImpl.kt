package com.bilal.mvvmsample.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bilal.mvvmsample.data.network.response.CurrentWeatherResponse
import com.bilal.mvvmsample.internal.NoConnectivityExceptions

class WeatherNetworkDataSourceImpl(
    private val apixuWeatherApiService: ApixuWeatherApiService
) : WeatherNetworkDataSource {

    //Encapsulation
    private val _downloadedCurrentWeather = MutableLiveData<CurrentWeatherResponse>()

    override val downloadedCurrentWeather: LiveData<CurrentWeatherResponse>
        get() = _downloadedCurrentWeather

    override suspend fun fetchCurrentWeather(location: String, languageCode: String) {
        try{
            val fetchedCurrentWeather = apixuWeatherApiService
                .getCurrentWeather(location,languageCode)
                .await()

            _downloadedCurrentWeather.postValue(fetchedCurrentWeather)
        }catch (e : NoConnectivityExceptions){
            Log.e("Connectivity","No Internet Connectivity",e)
        }

    }
}