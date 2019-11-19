package com.bilal.mvvmsample.data.network.response

import com.bilal.mvvmsample.data.db.entity.CurrentWeatherEntry
import com.bilal.mvvmsample.data.db.entity.Location
import com.bilal.mvvmsample.data.db.entity.Request
import com.google.gson.annotations.SerializedName


data class CurrentWeatherResponse(
    @SerializedName("current")
    val currentWeatherEntry: CurrentWeatherEntry,
    val location: Location,
    val request: Request
)