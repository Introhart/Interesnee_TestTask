package com.example.thesearcher.data.Network

import com.example.thesearcher.data.Network.Model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

val API_KEY = "2a79c66278f7ee20959e979e87016451b2da276a79fbae3e1406fada5935b72a"

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