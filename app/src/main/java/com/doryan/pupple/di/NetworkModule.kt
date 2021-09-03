package com.doryan.pupple.di

import com.doryan.pupple.network.DogApiService
import com.doryan.pupple.network.RandomNameApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "https://dog.ceo/api/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(moshi: Moshi): Retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl(BASE_URL)
            .build()

    @Provides
    @Singleton
    fun provideDogApiService(retrofit: Retrofit): DogApiService = retrofit.create(DogApiService::class.java)

    @Provides
    @Singleton
    fun provideRandomNameApiService(retrofit: Retrofit): RandomNameApiService = retrofit.create(RandomNameApiService::class.java)
}