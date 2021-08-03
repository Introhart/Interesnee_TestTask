package com.example.thesearcher.view_model

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.thesearcher.data.Network.Model.ImagesResult
import com.example.thesearcher.model.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

// TODO :: Needed in smth. better than Singleton
@Singleton
class MainActivityViewModel @Inject constructor(
    private val queryImageUseCaseProvider: Provider<QueryImagesUseCase>
): ViewModel() {

    init{
        Log.d("dbg", "New ViewModel : ${this.hashCode()}")
    }

    private var _query = MutableStateFlow("")

    private var query: StateFlow<String> = _query

    @ExperimentalCoroutinesApi
    var images: StateFlow<PagingData<ImagesResult>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Eagerly, PagingData.empty())

    private val newPagingSource: PagingSource<*, *>? = null

    private fun newPager(query: String): Pager<Int, ImagesResult> {
        Log.d("dbg", "New pager")
        return Pager(PagingConfig(pageSize = 100)) {
            Log.d("dbg", "New pager1")
            newPagingSource?.invalidate()
            val queryImagesUseCase = queryImageUseCaseProvider.get()
            queryImagesUseCase(query).also { newPagingSource }
        }
    }

    fun setQuery(query: String){
        Log.d("dbg", "Set query")
        _query.tryEmit(query)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModelProvider: Provider<MainActivityViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            Log.d("dbg", "Factory")
            require(modelClass == MainActivityViewModel::class.java)
            return viewModelProvider.get() as T
        }
    }
}

class QueryImagesUseCase @Inject constructor(private val repository: ImageRepository) {

    operator fun invoke(query: String) : PagingSource<Int, ImagesResult> {
        return repository.makeRequest(query)
    }
}