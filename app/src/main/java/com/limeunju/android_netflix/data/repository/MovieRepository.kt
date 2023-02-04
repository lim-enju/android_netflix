package com.limeunju.android_netflix.data.repository

import android.util.Log
import com.limeunju.android_netflix.data.model.response.MovieResponse
import com.limeunju.android_netflix.data.service.ApiService
import javax.inject.Inject

class MovieRepository @Inject constructor(private val apiService: ApiService){

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
        query:String, display:Int?=null, start:Int?=null, genre: String?=null, country: String?=null, yearfrom:Int?=null, yearto:Int?=null
    ): MovieResponse? {
        var result:MovieResponse?
        try {
            result = apiService.getMovies(query, display, start, genre, country, yearfrom, yearto)
        }catch (exception: Exception){
            result = null
        }
        return result
    }
}