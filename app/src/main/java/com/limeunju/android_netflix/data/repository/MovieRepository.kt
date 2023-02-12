package com.limeunju.android_netflix.data.repository

import android.util.Log
import com.limeunju.android_netflix.common.SearchConfig
import com.limeunju.android_netflix.data.database.Favorite.Favorite
import com.limeunju.android_netflix.data.datasource.MovieDataSource
import com.limeunju.android_netflix.data.model.response.MovieResponse
import com.limeunju.android_netflix.data.service.ApiService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDataSource: MovieDataSource
){
    val favorites =
        movieDataSource
            .favoriteMovies
            .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.WhileSubscribed(), listOf())
    suspend fun getMovies(
        query:String,
        display:Int?,
        start:Int?,
        yearfrom:Int?,
        yearto:Int?
    ): MovieResponse? {
        var result:MovieResponse?
        try {
            result = movieDataSource.getMovies(query, display, start, yearfrom, yearto)
            favorites.value.forEach { favorites ->
                result?.movies?.forEach {
                    if(it.title == favorites.name){
                        it.favorite = true
                    }
                }
            }
        }catch (exception: Exception){
            result = null
        }
        return result
    }
    suspend fun saveFavorite(name: String)
        = movieDataSource.saveFavorite(name)

    suspend fun deleteFavorite(name: String)
        = movieDataSource.deleteFavorite(name)
}