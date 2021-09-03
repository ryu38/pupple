package com.doryan.pupple.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.doryan.pupple.model.FavDog

@Database(entities = [FavDog::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract val favDogDao: FavDogDao
}