package com.doryan.pupple.repository

import com.doryan.pupple.database.FavDogDao
import com.doryan.pupple.model.FavDog
import com.doryan.pupple.network.DogApiService
import com.doryan.pupple.network.RandomNameApiService
import com.doryan.pupple.network.response.DogProperty
import com.doryan.pupple.network.response.RandomNameProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

class Repository @Inject constructor(
    private val dogApi: DogApiService,
    private val favDogDao: FavDogDao,
    private val randomNameApi: RandomNameApiService
) {

    suspend fun getDogs(): List<DogProperty> = dogApi.getDogs().properties

    suspend fun getFavDogs() = favDogDao.getAll()

    suspend fun registerFavDog(favDog: FavDog) {
        favDogDao.insert(favDog)
    }

    suspend fun getRandomName() = randomNameApi.getRandomName().property
}