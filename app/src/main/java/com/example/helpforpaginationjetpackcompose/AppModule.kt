package com.example.helpforpaginationjetpackcompose

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val BASEURL = "https://jsonplaceholder.typicode.com"
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun getPhotoapi():PhotoApi{
        return Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PhotoApi::class.java)
    }

    @Provides
    @Singleton
    fun getPhotoRepository(photoApi: PhotoApi) = PhotoRepository(photoApi)

}