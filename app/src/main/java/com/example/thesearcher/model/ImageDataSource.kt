package com.example.thesearcher.model

interface ImageDataSource {
    fun retrieveImages(callback: OperationCallback<ImageInfo>, searchRequest: String)
    fun retrieveNext(callback: OperationCallback<ImageInfo>)
    fun retrievePrev(callback: OperationCallback<ImageInfo>)
}