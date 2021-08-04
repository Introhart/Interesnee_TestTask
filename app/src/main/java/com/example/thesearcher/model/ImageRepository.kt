package com.example.thesearcher.model

import androidx.paging.PagingSource
import com.example.thesearcher.data.ImagePageSource
import com.example.thesearcher.data.Network.Model.ImagesResult
import javax.inject.Inject

class ImageRepository @Inject constructor(
    private val imagePageSource: ImagePageSource.Factory
) {

    fun makeRequest(query: String): PagingSource<Int, ImagesResult> {
        return imagePageSource.create(query)
    }
}