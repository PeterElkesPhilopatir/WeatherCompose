package com.peter.weathercompose.data

import androidx.room.*
import com.peter.weathercompose.model.Favorite
import com.peter.weathercompose.model.Unit

import kotlinx.coroutines.flow.Flow
@Dao
interface WeatherDao {
    @Query(value = "select * from fav_tbl")
    fun getFavorites(): Flow<List<Favorite>>

    @Query(value = "select * from fav_tbl where city =:city")
    fun getFavByCity(city: String): Favorite

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fav: Favorite)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(note: Favorite)

    @Query(value = "DELETE from fav_tbl")
    suspend fun deleteAll()

    @Delete
    suspend fun delete(fav: Favorite)


    @Query("SELECT * from settings_tbl")
    fun getUnits(): Flow<List<Unit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUnit(unit: Unit)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateUnit(unit: Unit)

    @Query("DELETE from settings_tbl")
    suspend fun deleteAllUnits()

    @Delete
    suspend fun deleteUnit(unit: Unit)

}