package com.limeunju.android_netflix.data.usecase

import com.limeunju.android_netflix.data.repository.MovieRepository
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val movieRepository: MovieRepository
) {
    suspend fun searchMovie(query: String) =
        movieRepository.getMovies(query)

}