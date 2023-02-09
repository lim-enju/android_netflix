package com.limeunju.android_netflix.data.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.limeunju.android_netflix.common.SearchConfig
import com.limeunju.android_netflix.data.datasource.MoviePagingSource
import com.limeunju.android_netflix.data.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    fun getMovies(query: String) = Pager(PagingConfig(pageSize = SearchConfig.SEARCHED_DISPLAY_NUM)) {
        MoviePagingSource(query, movieRepository)
    }.flow
}