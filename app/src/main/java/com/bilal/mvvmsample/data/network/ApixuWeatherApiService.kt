package com.bilal.mvvmsample.data.network

import com.bilal.mvvmsample.data.network.response.CurrentWeatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "a18ed596ebbc2e32df759c48fe58023a"

//  http://api.weatherstack.com/current?access_key = YOUR_ACCESS_KEY & query = New York

interface ApixuWeatherApiService {

    @GET("current")
    fun getCurrentWeather(
        @Query("query") location: String,
        @Query("lang") languageCOde: String = "en" //do
    ): Deferred<CurrentWeatherResponse>


    companion object { //static methods
        operator fun invoke(connectivityInterceptor: ConnectivityInterceptor): ApixuWeatherApiService {
            val reqestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("access_key", API_KEY)
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(reqestInterceptor) //For adding Api Key in request
                .addInterceptor(connectivityInterceptor) //For checking connectivity status, before api call
               // .addInterceptor(ConnectivityInterceptorImpl()) //it creates tight coupling,later we use DI
                // .authenticator() //for authentication
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.weatherstack.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory()) //Using Caroutine. for Deferred  //Like RxCallAdapterFactory we use this for caroutine
                .addConverterFactory(GsonConverterFactory.create())  //Getting response as a Model class with the help of GSON.
                .build()
                .create(ApixuWeatherApiService::class.java)

        }

        /* operator fun create() : ApixuWeatherApiService{
         }*/


    }
}