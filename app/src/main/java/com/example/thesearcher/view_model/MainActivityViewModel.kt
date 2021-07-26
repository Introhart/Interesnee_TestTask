package com.example.thesearcher.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.thesearcher.model.ImageInfo
import com.example.thesearcher.model.ImageRepository
import com.example.thesearcher.model.OperationCallback

class MainActivityViewModel(private val repository: ImageRepository): ViewModel() {
    var images : MutableLiveData<MutableList<ImageInfo>> = MutableLiveData<MutableList<ImageInfo>>()

    fun getImagesOnRequest(searchRequest: String) {
        repository.fetchImages(object: OperationCallback<ImageInfo> {

            override fun onSuccess(data: MutableList<ImageInfo>?) {
                images.postValue(data)
            }

            override fun onError(error: String?) {
                TODO("Not yet implemented")
            }
        },
        searchRequest
        )
    }
    //todo :: FIX ME
    fun getNextPage() {
        repository.fetchNextPage(object: OperationCallback<ImageInfo> {

            override fun onSuccess(data: MutableList<ImageInfo>?) {
//                images.postValue(data)
                var dt : MutableList<ImageInfo> = mutableListOf()
                for(item in images.value!!) {
                    dt.add(item)
                }
                if (data != null) {
                    for(item in data){
                        dt.add(item)
                    }
                }
                images.postValue(data)
            }

            override fun onError(error: String?) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getPrevPage() {
        repository.fetchPrevPage(object: OperationCallback<ImageInfo> {

            override fun onSuccess(data: MutableList<ImageInfo>?) {
                images.postValue(data)
            }

            override fun onError(error: String?) {
                TODO("Not yet implemented")
            }
        })
    }
}