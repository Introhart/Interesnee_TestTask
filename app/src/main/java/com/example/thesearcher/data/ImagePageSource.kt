package com.example.thesearcher.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.thesearcher.data.Network.ImageApi
import com.example.thesearcher.data.Network.Model.ImagesResult
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import retrofit2.HttpException

class ImagePageSource @AssistedInject constructor(
    private val service: ImageApi,
    @Assisted("query") private val query: String,
) : PagingSource<Int, ImagesResult>() {

    private val PAGE_SIZE = 100

    override fun getRefreshKey(state: PagingState<Int, ImagesResult>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ImagesResult> {

        if (query.isEmpty()){
            return LoadResult.Page(emptyList(), prevKey = null, nextKey = null)
        }

        try{
            val page: Int = params.key ?: 1
            val response = service.getImages(
                query,
                "isch",
                0,
                "google"
            )

            return if (response.isSuccessful) {
                val result = response.body()?.images_results

                val nextKey = if (result?.size!! < PAGE_SIZE) null else page + 1
                val prevKey = if (page == 1) null else page - 1

                LoadResult.Page(result, prevKey, nextKey)
            } else {
                LoadResult.Error(HttpException(response))
            }
        } catch(e: HttpException){
            return LoadResult.Error(e)
        } catch (e: Exception){
            return LoadResult.Error(e)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted("query") query: String): ImagePageSource
    }
}

