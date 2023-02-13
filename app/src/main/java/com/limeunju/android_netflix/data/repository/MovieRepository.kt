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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.transform
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val movieDataSource: MovieDataSource
){
    val favorites =
        movieDataSource
            .favoriteMovies
            .transform { list ->
                emit( list.map{ it.name } )
            }
            .stateIn(CoroutineScope(Dispatchers.IO), SharingStarted.WhileSubscribed(), listOf())
    suspend fun getMovies(
        query:String,
        display:Int? = null,
        start:Int? = null,
        yearfrom:Int? = null,
        yearto:Int? = null,
    ): MovieResponse? {
        var result:MovieResponse?
        try {
            result = movieDataSource.getMovies(query, display, start, yearfrom, yearto)
            result?.movies?.forEach {
                it.title = it.title?.replace("<b>", "")?.replace("</b>", "")
            }
        }catch (exception: Exception){
            result = null
        }
        return result
    }
    fun saveFavorite(name: String)
        = movieDataSource.saveFavorite(name)

    fun deleteFavorite(name: String) {
        Log.d("EJLIM", "delete $name")
        movieDataSource.deleteFavorite(name)
    }
}