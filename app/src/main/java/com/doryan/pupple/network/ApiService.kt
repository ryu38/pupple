package com.doryan.pupple.network

import com.doryan.pupple.network.response.DogResponse
import com.doryan.pupple.network.response.RandomNameResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface DogApiService {
    @GET("breeds/image/random/{dogNum}")
    suspend fun getDogs(@Path("dogNum") dogNum: Int = 10): DogResponse
}

interface RandomNameApiService {
    @GET("https://randomuser.me/api")
    suspend fun getRandomName(): RandomNameResponse
}