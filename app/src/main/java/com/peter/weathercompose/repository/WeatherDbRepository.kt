package com.peter.weathercompose.repository

import com.peter.weathercompose.data.WeatherDao
import com.peter.weathercompose.model.Favorite
import com.peter.weathercompose.model.Unit
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WeatherDbRepository @Inject constructor(private val dao: WeatherDao) {

    fun getFavorites(): Flow<List<Favorite>> = dao.getFavorites()
    suspend fun insertFavorite(favorite: Favorite) = dao.insert(favorite)
    suspend fun updateFavorite(favorite: Favorite) = dao.update(favorite)
    suspend fun deleteAllFavorite() = dao.deleteAll()
    suspend fun deleteFavorite(favorite: Favorite) = dao.delete(favorite)
    fun getFav(city: String) = dao.getFavByCity(city)


    fun getUnits(): Flow<List<Unit>> = dao.getUnits()
    suspend fun insertUnit(unit: Unit) = dao.insertUnit(unit)
    suspend fun updateUnit(unit: Unit) = dao.updateUnit(unit)
    suspend fun deleteAllUnits() = dao.deleteAllUnits()
    suspend fun deleteUnit(unit: Unit) = dao.deleteUnit(unit)
}