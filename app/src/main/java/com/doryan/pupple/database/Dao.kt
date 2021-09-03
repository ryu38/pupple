package com.doryan.pupple.database

import androidx.room.*
import com.doryan.pupple.model.FavDog

@Dao
interface FavDogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favDog: FavDog)

    @Update
    suspend fun update(favDog: FavDog)

    @Delete
    suspend fun delete(favDog: FavDog)

    @Query("SELECT * FROM favorite_dogs ORDER BY id DESC")
    suspend fun getAll(): List<FavDog>
}