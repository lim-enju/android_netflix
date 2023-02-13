package com.limeunju.android_netflix.data.datasource

import com.limeunju.android_netflix.common.SearchConfig
import com.limeunju.android_netflix.data.database.Favorite.Favorite
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.data.model.response.toFavorite
import com.limeunju.android_netflix.data.module.AppDatabase
import com.limeunju.android_netflix.data.service.ApiService
import javax.inject.Inject

class MovieDataSource @Inject constructor(
    private var database: AppDatabase,
    private val apiService: ApiService,
){
    val favoriteMovies =
        database
            .favoriteDao()
            .getAll()

    suspend fun getMovies(
        query:String,
        display:Int? = SearchConfig.SEARCHED_DISPLAY_NUM,
        start:Int? = 1,
        yearfrom:Int? = SearchConfig.SEARCHED_START_DATE,
        yearto:Int? = SearchConfig.SEARCHED_END_DATE
    ) = apiService.getMovies(query, display, start, yearfrom, yearto)
    fun saveFavorite(movie: Movie) {
        database
            .favoriteDao()
            .insert(movie.toFavorite())
    }

    fun deleteFavorite(movie: Movie) {
        movie.title?.let {
            database
                .favoriteDao()
                .delete(it)
        }
    }
}