package com.doryan.pupple.repository

import com.doryan.pupple.database.FavDogDao
import com.doryan.pupple.model.FavDog
import com.doryan.pupple.model.SwipeDog
import com.doryan.pupple.network.DogApiService
import com.doryan.pupple.network.RandomNameApiService
import com.doryan.pupple.network.response.DogProperty
import com.doryan.pupple.network.response.RandomNameProperty
import com.doryan.pupple.utils.matrixTranspose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val dogApi: DogApiService,
    private val favDogDao: FavDogDao,
    private val randomNameApi: RandomNameApiService
) {

    suspend fun getDogs(): List<SwipeDog> {
        return coroutineScope {
            val asyncDogs = async { getDogImages() }
            val asyncName = async { getRandomName() }
            matrixTranspose(listOf(asyncDogs.await(), asyncName.await())).map {
                SwipeDog(
                    (it[0] as DogProperty).imageUrl,
                    (it[1] as RandomNameProperty).firstName
                )
            }
        }
    }

    suspend fun getDogImages(): List<DogProperty> = dogApi.getDogs().properties

    suspend fun getRandomName() = randomNameApi.getRandomName().properties

    suspend fun getFavDogs() = favDogDao.getAll()

    suspend fun registerFavDog(favDog: FavDog) {
        favDogDao.insert(favDog)
    }
}