package com.limeunju.android_netflix.data.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.limeunju.android_netflix.common.SearchConfig
import com.limeunju.android_netflix.data.datasource.MoviePagingSource
import com.limeunju.android_netflix.data.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    val favorites = movieRepository.favorites
    fun getMovies(query: String) = Pager(PagingConfig(pageSize = SearchConfig.SEARCHED_DISPLAY_NUM)) {
        MoviePagingSource(query, movieRepository)
    }.flow

    fun saveFavorite(name: String){
        movieRepository.saveFavorite(name)
    }

    fun deleteFavorite(name: String){
        movieRepository.deleteFavorite(name)
    }
}