package com.example.thesearcher.DI

import com.example.thesearcher.data.Network.ImageApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideImageService(): ImageApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://serpapi.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofit.create(ImageApi::class.java)
    }
}