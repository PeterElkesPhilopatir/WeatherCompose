package com.peter.weathercompose.di

import android.content.Context
import androidx.room.Room
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.peter.weathercompose.data.WeatherDao
import com.peter.weathercompose.data.WeatherDatabase
import com.peter.weathercompose.network.WeatherAPI
import com.peter.weathercompose.repository.WeatherDbRepository
import com.peter.weathercompose.repository.WeatherRepository
import com.peter.weathercompose.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideDbRepository(dao: WeatherDao): WeatherDbRepository = WeatherDbRepository(dao)
    @Singleton
    @Provides
    fun provideDao(wdb: WeatherDatabase): WeatherDao = wdb.weatherDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): WeatherDatabase
            = Room.databaseBuilder(
        context,
        WeatherDatabase::class.java,
        "weather_db")
        .fallbackToDestructiveMigration()
        .build()


    @Singleton
    @Provides
    fun provideQuestionRepository(api: WeatherAPI) = WeatherRepository(api)

    @Singleton
    @Provides
    fun provideQuestionApi(): WeatherAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(WeatherAPI::class.java)
    }
}