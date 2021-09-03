package com.doryan.pupple.ui.fav

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doryan.pupple.model.FavDog
import com.doryan.pupple.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class FavViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _favDogs = MutableLiveData<List<FavDog>>()
    val favDogs: LiveData<List<FavDog>>
        get() = _favDogs

    val randomMessage: String
        get() = messageList[Random.nextInt(messageList.size)]

    init {
        getFavDogs()
    }

    private fun getFavDogs() {
        viewModelScope.launch {
            _favDogs.value = repository.getFavDogs()
        }
    }

    companion object {
        val messageList = listOf(
            "こんにちわん🐶",
            "エサをください🍖",
            "散歩に連れ行ってください🚶‍♀🐕️",
            "エサが足りません！😠"
        )
    }
}