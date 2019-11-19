package com.bilal.mvvmsample.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bilal.mvvmsample.data.db.dao.CurrentWeatherDao
import com.bilal.mvvmsample.data.db.entity.CurrentWeatherEntry

@Database(
    entities = [CurrentWeatherEntry::class],
    version = 1
)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object {

        @Volatile
        private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabse(context).also { instance = it }
        }

        private fun buildDatabse(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java, "forecast.db"
            ).build()

    }
}