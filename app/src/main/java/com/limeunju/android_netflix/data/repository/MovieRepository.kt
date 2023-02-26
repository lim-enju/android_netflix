package com.limeunju.android_netflix.data.repository

import android.net.Uri
import android.util.Log
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
                    .forEach { favorite -> map[favorite.fid] = favorite }
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
                response.movies.removeIf { it.title.isNullOrBlank() || it.link.isNullOrBlank() || it.image.isNullOrBlank() }
                response.movies.sortByDescending { it.pubDate }
                response.movies.forEach { movie ->
                    //태그 제거
                    movie.title = movie.title?.replace("<b>", "")?.replace("</b>", "")

                    //code 분리
                    val uri = Uri.parse(movie.link)
                    uri.getQueryParameter("code")?.let {
                        movie.fid = it.toInt()
                    }
                }
            }
            .onFailure {  }

    fun saveFavorite(movie: Movie)
        = movieDataSource.saveFavorite(movie)

    fun deleteFavorite(movie: Movie) {
        movieDataSource.deleteFavorite(movie)
    }
}