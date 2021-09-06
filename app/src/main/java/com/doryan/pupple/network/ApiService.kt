package com.doryan.pupple.network

import com.doryan.pupple.network.response.DogResponse
import com.doryan.pupple.network.response.RandomNameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DogApiService {
    @GET("breeds/image/random/{dogNum}")
    suspend fun getDogs(@Path("dogNum") dogNum: Int = 30): DogResponse
}

interface RandomNameApiService {
    @GET("https://randomuser.me/api/")
    suspend fun getRandomName(@Query("results") nameNum: Int = 30): RandomNameResponse
}