package com.example.thesearcher.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.example.thesearcher.data.Network.Model.ImagesResult
import com.example.thesearcher.model.ImageRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Provider

class MainActivityViewModel @Inject constructor(
    private val queryImageUseCaseProvider: Provider<QueryImagesUseCase>
): ViewModel() {

    private val _query = MutableStateFlow("")
    private val query: StateFlow<String> = _query.asStateFlow()

    @ExperimentalCoroutinesApi
    val images: StateFlow<PagingData<ImagesResult>> = query
        .map(::newPager)
        .flatMapLatest { pager -> pager.flow }
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private val newPagingSource: PagingSource<*, *>? = null

    private fun newPager(query: String): Pager<Int, ImagesResult> {
        return Pager(PagingConfig(pageSize = 100)) {
            newPagingSource?.invalidate()
            val queryImagesUseCase = queryImageUseCaseProvider.get()
            queryImagesUseCase(query).also { newPagingSource}
        }
    }

    fun setQuery(query: String){
        _query.tryEmit(query)
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val viewModelProvider: Provider<MainActivityViewModel>
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
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