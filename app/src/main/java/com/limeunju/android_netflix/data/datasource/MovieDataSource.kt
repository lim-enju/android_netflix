package com.limeunju.android_netflix.data.datasource

import com.limeunju.android_netflix.common.SearchConfig
import com.limeunju.android_netflix.data.database.Favorite.Favorite
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
    suspend fun saveFavorite(name: String) {
        database
            .favoriteDao()
            .insert(Favorite(name= name))
    }

    suspend fun deleteFavorite(name: String) {
        database
            .favoriteDao()
            .delete(Favorite(name= name))
    }
}