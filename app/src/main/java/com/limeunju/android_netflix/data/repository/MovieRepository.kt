package com.limeunju.android_netflix.data.repository

import android.util.Log
import com.limeunju.android_netflix.BuildConfig
import com.limeunju.android_netflix.data.datasource.MovieDataSource
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.data.model.response.MovieResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDataSource: MovieDataSource
){
    val favorites =
        movieDataSource
            .favoriteMovies
            .map {list ->
                val map = mutableMapOf<Int, Movie>()
                list
                    .filterNot { it.title.isNullOrBlank()}
                    .forEach { favorite -> map[favorite.id] = favorite }
                map
            }
            .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.WhileSubscribed(), mapOf())

    suspend fun getMovies(
        query:String,
        display:Int? = null,
        page:Int? = null,
    ): Result<MovieResponse> =
         movieDataSource.getMovies(query, display, page)
            .onSuccess { response ->
                val movies = ArrayList(response.movies?: listOf())
                movies.sortByDescending { it.releaseDate }
                movies.removeIf { it.title.isNullOrEmpty() || it.posterPath.isNullOrEmpty()}
                movies.map { movie ->
                        Log.d("TAG", "getMovies: ${movie.title} ${movie.posterPath}")
                        movie.posterPath = BuildConfig.IMAGE_URL + movie.posterPath
                    }
            }
            .onFailure {  }

    fun saveFavorite(movie: Movie)
        = movieDataSource.saveFavorite(movie)

    fun deleteFavorite(movie: Movie) {
        movieDataSource.deleteFavorite(movie)
    }
}