package com.example.thesearcher.data.Network

import com.example.thesearcher.data.Network.Model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

val API_KEY = "95b9807bd29bb6e5755b8449a8e70378abb371ca9663a05234a253c9f0ce7376"

interface ImageApi {

    @GET("/search")
    suspend fun getImages(
        @Query("q") query: String,
        @Query("tbm") tbm: String,
        @Query("ijn") ijn: Int,
        @Query("engine") engine: String,
        @Query("api_key") apiKey: String = API_KEY
    ) : Response<ImageResponse>
}