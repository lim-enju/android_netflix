package com.limeunju.android_netflix.data.datasource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.limeunju.android_netflix.data.model.response.Items
import com.limeunju.android_netflix.data.model.response.MovieResponse
import com.limeunju.android_netflix.data.repository.MovieRepository
import com.limeunju.android_netflix.data.service.ApiService
import com.limeunju.android_netflix.data.values.nextKey
import com.limeunju.android_netflix.data.values.prevKey
import javax.inject.Inject

class MoviePagingSource constructor(
    private val query:String,
    private val movieRepository: MovieRepository
): PagingSource<Int, Items>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Items> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = movieRepository.getMovies(query)
            Log.d("EJLIM", "Searched $query ${response?.items?.size}")
            LoadResult.Page(
                data = response?.items?: arrayListOf(),
                prevKey = params.prevKey(),
                nextKey = params.nextKey(response?.total?:0)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Items>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
}