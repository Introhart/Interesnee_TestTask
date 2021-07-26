package com.example.thesearcher.model

class ImageRepository(private val imageDataSource: ImageDataSource) {

    fun fetchImages(callback: OperationCallback<ImageInfo>, searchRequest: String) {
        imageDataSource.retrieveImages(callback, searchRequest)
    }

    fun fetchNextPage(callback: OperationCallback<ImageInfo>) {
        imageDataSource.retrieveNext(callback)
    }

    fun fetchPrevPage(callback: OperationCallback<ImageInfo>) {
        imageDataSource.retrievePrev(callback)
    }
}