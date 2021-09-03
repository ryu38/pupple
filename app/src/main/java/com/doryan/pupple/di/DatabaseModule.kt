package com.doryan.pupple.di

import android.app.Application
import androidx.room.Room
import com.doryan.pupple.database.AppDatabase
import com.doryan.pupple.database.FavDogDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): AppDatabase {
        return Room.databaseBuilder(
            application, AppDatabase::class.java, "favdog_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideFavDogDao(appDatabase: AppDatabase): FavDogDao {
        return appDatabase.favDogDao
    }
}