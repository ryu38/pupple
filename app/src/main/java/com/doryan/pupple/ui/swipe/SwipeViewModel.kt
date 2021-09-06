package com.doryan.pupple.ui.swipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doryan.pupple.model.FavDog
import com.doryan.pupple.model.SwipeDog
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

    private val _dogs = MutableLiveData<List<SwipeDog>>()
    val dogs: LiveData<List<SwipeDog>>
        get() = _dogs

    private val _status = MutableLiveData<DogApiStatus>()
    val status: LiveData<DogApiStatus>
        get() = _status

    init {
        initGetDogs()
    }

    private fun initGetDogs() {
        viewModelScope.launch {
            _status.value = DogApiStatus.LOADING
            try {
                _dogs.value = repository.getDogs()
                _status.value = DogApiStatus.DONE
            } catch (e: Exception) {
                _dogs.value = ArrayList()
                _status.value = DogApiStatus.ERROR
            }
        }
    }

    fun pass() {
        viewModelScope.launch {
            _dogs.value?.let {
                var result = it.drop(1)
                Timber.i("left: ${result.size}")
                if (result.size <= 5) {
                    result = listOf(result, repository.getDogs()).flatten()
                }
                _dogs.value = result
            }
        }
    }

    fun fav() {
        viewModelScope.launch {
            _dogs.value?.let {
                val targetDog = it[0]
                val favDog = FavDog(
                    imgUrl = targetDog.imageUrl,
                    name = targetDog.name
                )
                repository.registerFavDog(favDog)
                pass()
            }
        }
    }
}