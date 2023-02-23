package com.limeunju.android_netflix.data.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.limeunju.android_netflix.common.SearchConfig
import com.limeunju.android_netflix.data.datasource.MoviePagingSource
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.data.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    val favorites = movieRepository.favorites
    fun getMoviesPager(query: String) = Pager(PagingConfig(pageSize = SearchConfig.SEARCHED_DISPLAY_NUM)) {
        MoviePagingSource(query, movieRepository)
    }.flow

    suspend fun getMovie(query: String) =
        movieRepository.getMovies(query)
            .getOrNull()
            ?.movies
            ?.singleOrNull()

    fun saveFavorite(movie: Movie){
        movieRepository.saveFavorite(movie)
    }

    fun deleteFavorite(movie: Movie){
        movieRepository.deleteFavorite(movie)
    }
}