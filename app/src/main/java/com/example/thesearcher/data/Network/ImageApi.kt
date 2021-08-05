package com.example.thesearcher.data.Network

import com.example.thesearcher.data.Network.Model.ImageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

val API_KEY = "bd0387cfe3b15afa2137bd71d9f4f28d332f60bf5dc3fad8983eb4b9dee15e80"

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