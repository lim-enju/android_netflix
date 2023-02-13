package com.limeunju.android_netflix.data.datasource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.data.repository.MovieRepository
import com.limeunju.android_netflix.data.values.nextKey
import com.limeunju.android_netflix.data.values.prevKey

class MoviePagingSource constructor(
    private val query:String,
    private val movieRepository: MovieRepository
): PagingSource<Int, Movie>() {
    //LoadParams: 로드할 키와 항목 수
    //LoadResult: 로드 작업의 결과
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = movieRepository.getMovies(query, start = nextPageNumber)
            LoadResult.Page(
                data = response?.movies?: arrayListOf(),
                prevKey = params.prevKey(),
                nextKey = params.nextKey(response?.total?:0)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int =
        ((state.anchorPosition ?: 0) - state.config.initialLoadSize / 2)
            .coerceAtLeast(0)
}