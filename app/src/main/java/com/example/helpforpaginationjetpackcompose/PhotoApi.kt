package com.example.helpforpaginationjetpackcompose

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface PhotoApi {

    @GET("/albums/{page}/photos")
    suspend fun getphotos(
        @Path("page") page: Int
    ): List<MainPhoto>
}