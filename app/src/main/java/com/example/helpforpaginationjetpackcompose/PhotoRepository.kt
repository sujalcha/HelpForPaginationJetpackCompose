package com.example.helpforpaginationjetpackcompose

import android.util.Log
import dagger.hilt.android.scopes.ActivityScoped
import retrofit2.Response
import javax.inject.Inject

@ActivityScoped
class PhotoRepository @Inject constructor(
    private val photoApi: PhotoApi
){
    suspend fun getphotos(page:Int): Resource<List<MainPhoto>> {
        val response = try{
            photoApi.getphotos(page)
        }catch (e:Exception){
            Log.d("Check","Error")
            return Resource.Error("An unknown error occured.")
        }
        return Resource.Success(response)
    }
}