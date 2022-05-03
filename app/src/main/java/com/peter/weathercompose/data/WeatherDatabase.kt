package com.peter.weathercompose.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.peter.weathercompose.model.Favorite
import com.peter.weathercompose.model.Unit

@Database(entities = [Favorite::class,Unit::class], version = 2, exportSchema = false)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun weatherDao(): WeatherDao
}