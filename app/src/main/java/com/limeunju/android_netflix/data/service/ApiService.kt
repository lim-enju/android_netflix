package com.limeunju.android_netflix.data.service

import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.data.model.response.MovieResponse
import retrofit2.http.*

interface ApiService {
    @Headers(
        "Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI3NTVjYTcwM2I5ZjQ1OGY2Y2Y1MzQ5Nzg4NjEwZjE1NyIsInN1YiI6IjY1NzZiZGQ3NTY0ZWM3MDBlMTBjNGY1NiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.cyBJ-yRcayGCOsGeBYF4tCPKF8aNp2wOtAWGPLsn490",
        "accept: application/json"
    )
    @GET("search/movie")
    suspend fun getMovies(
        @Query("query") query:String,
        @Query("display") display: Int?,
        @Query("include_adult") adualt: Boolean,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Result<MovieResponse>
}