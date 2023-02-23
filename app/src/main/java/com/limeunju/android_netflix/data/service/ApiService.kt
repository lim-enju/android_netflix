package com.limeunju.android_netflix.data.service

import com.limeunju.android_netflix.data.model.response.MovieResponse
import okhttp3.Response
import retrofit2.http.*

interface ApiService {
    @Headers(
        "X-Naver-Client-Id: ToyQRxLOqeiB9J7R6M2w",
        "X-Naver-Client-Secret: O6EH_yor97"
    )
    @GET("search/movie.json")
    suspend fun getMovies(
        @Query("query") query:String,
        @Query("display") display: Int?,
        @Query("start") start: Int?,
        @Query("yearfrom") yearfrom: Int?,
        @Query("yearto") yearto: Int?
    ): Result<MovieResponse>
}