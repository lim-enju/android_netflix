package com.limeunju.android_netflix.data.repository

import android.util.Log
import com.limeunju.android_netflix.common.SearchConfig
import com.limeunju.android_netflix.data.model.response.MovieResponse
import com.limeunju.android_netflix.data.service.ApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(
    private val apiService: ApiService
){
    suspend fun getMovies(querys: ArrayList<String>): Map<String, MovieResponse>{
        val response: MutableMap<String, MovieResponse> = mutableMapOf()
        querys
            .map {query ->
                val result = getMovies(query)
                if(result != null){
                    response[query] = result
                }
            }
        return response
    }

    suspend fun getMovies(
        query:String,
        display:Int = SearchConfig.SEARCHED_DISPLAY_NUM,
        start:Int = 1,
        yearfrom:Int = SearchConfig.SEARCHED_START_DATE,
        yearto:Int = SearchConfig.SEARCHED_END_DATE
    ): MovieResponse? {
        var result:MovieResponse?
        try {
            result = apiService.getMovies(query, display, start, yearfrom, yearto)
        }catch (exception: Exception){
            result = null
        }
        return result
    }
}