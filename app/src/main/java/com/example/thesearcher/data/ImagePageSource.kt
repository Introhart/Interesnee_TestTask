package com.example.thesearcher.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.thesearcher.data.Network.ImageApi
import com.example.thesearcher.data.Network.Model.ImagesResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject


class ImagePageSource @AssistedInject constructor(
    private val service: ImageApi,
    @Assisted("query") private val query: String,
) : PagingSource<Int, ImagesResult>() {

    override fun getRefreshKey(state: PagingState<Int, ImagesResult>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    // TODO :: REFACTOR ME
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImagesResult> {

        if (query.isEmpty()){
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        val page: Int = params.key ?: 1
        val response = service.getImages(
            "Apple",
            "isch",
            0,
            "google"
        ) // TODO :: USE RESOURCES

        if (response.isSuccessful) {
            val result = response.body()?.images_results

            val nextKey = if (result?.size!! < 100) null else page + 1
            val prevKey = if (page == 1) null else page - 1

            return LoadResult.Page(result, prevKey, nextKey)
        } else {
//            TODO :: HTTP EXCEPTION
            Log.d("dbg", "Not yet implemented")
        }
        TODO("Not yet implemented")
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("query") query: String): ImagePageSource
    }
}

