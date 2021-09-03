package com.doryan.pupple.ui.swipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doryan.pupple.model.FavDog
import com.doryan.pupple.network.response.DogProperty
import com.doryan.pupple.network.response.RandomNameProperty
import com.doryan.pupple.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

enum class DogApiStatus { LOADING, DONE, ERROR }

@HiltViewModel
class SwipeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _dogs = MutableLiveData<List<DogProperty>>()
    val dogs: LiveData<List<DogProperty>>
        get() = _dogs

    private val _status = MutableLiveData<DogApiStatus>()
    val status: LiveData<DogApiStatus>
        get() = _status

    private val _currentDogName = MutableLiveData<RandomNameProperty>()
    val currentDogName: LiveData<RandomNameProperty>
        get() = _currentDogName

    init {
        getDogs()
    }

    private fun getDogs() {
        viewModelScope.launch {
            _status.value = DogApiStatus.LOADING
            try {
                val asyncDogs = async { repository.getDogs() }
                val asyncName = async { repository.getRandomName() }
                _dogs.value = asyncDogs.await()
                _currentDogName.value = asyncName.await()
                _status.value = DogApiStatus.DONE
            } catch (e: Exception) {
                _dogs.value = ArrayList()
                _currentDogName.value = RandomNameProperty()
                _status.value = DogApiStatus.ERROR
            }
        }
    }

    fun pass() {
        viewModelScope.launch {
            _dogs.value?.let {
                val asyncName = async { repository.getRandomName() }
                var result = it.drop(1)
                if (result.size <= 1) {
                    result = listOf(result, repository.getDogs()).flatten()
                }
                _dogs.value = result
                _currentDogName.value = asyncName.await()
            }
        }
    }

    fun fav() {
        viewModelScope.launch {
            _dogs.value?.let {
                val targetDog = it[0]
                val favDog = FavDog(
                    imgUrl = targetDog.imageUrl,
                    name = (_currentDogName.value)?.firstName ?: ""
                )
                repository.registerFavDog(favDog)
                pass()
            }
        }
    }
}