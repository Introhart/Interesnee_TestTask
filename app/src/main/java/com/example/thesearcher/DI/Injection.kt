package com.example.thesearcher.DI

import androidx.lifecycle.ViewModelProvider
import com.example.thesearcher.model.ImageRemoteDataSource
import com.example.thesearcher.model.ImageRepository
import com.example.thesearcher.view_model.MainViewModelFactory

object Injection {
    private val imageDataSource: ImageRemoteDataSource = ImageRemoteDataSource() // api ???
    private val imageRepository = ImageRepository(imageDataSource)
    private val viewModelFactory = MainViewModelFactory(imageRepository)


    fun provideRepository(): ImageRepository {
        return imageRepository
    }


    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }
}