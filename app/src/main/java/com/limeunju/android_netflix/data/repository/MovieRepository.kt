package com.limeunju.android_netflix.data.repository

import com.limeunju.android_netflix.data.database.Favorite.toMovie
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
                val map = mutableMapOf<String, Movie>()
                list
                    .filterNot { it.title.isNullOrBlank()}
                    .forEach { favorite -> map[favorite.title?:""] = favorite.toMovie() }
                map
            }
            .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.WhileSubscribed(), mapOf())
    suspend fun getMovies(
        query:String,
        display:Int? = null,
        start:Int? = null,
        yearfrom:Int? = null,
        yearto:Int? = null,
    ): Result<MovieResponse> =
         movieDataSource.getMovies(query, display, start, yearfrom, yearto)
            .onSuccess { response ->
                response.movies.forEach {
                    it.title = it.title?.replace("<b>", "")?.replace("</b>", "")
                }
            }
            .onFailure {  }

    fun saveFavorite(movie: Movie)
        = movieDataSource.saveFavorite(movie)

    fun deleteFavorite(movie: Movie) {
        movieDataSource.deleteFavorite(movie)
    }
}