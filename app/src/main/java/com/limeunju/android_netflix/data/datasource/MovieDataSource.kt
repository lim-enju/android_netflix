package com.limeunju.android_netflix.data.datasource

import com.limeunju.android_netflix.common.SearchConfig
import com.limeunju.android_netflix.data.model.response.Movie
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

    //flow로 값을 수신해야 하는 이유가 있을지?
    //suspend로 구현하면 그냥 반환받기만 하면 되는데
    //collect를 하기 위한 코드를 추가로 작성하는게 더 복잡하지 않을까?
    //favorite은 값을 계속 들고있다가 다른데에서 쓰여야 하니까 stateIn을 사용하는게 맞을것같다
    suspend fun getMovies(
        query:String,
        display:Int? = SearchConfig.SEARCHED_DISPLAY_NUM,
        yearfrom:Int? = SearchConfig.SEARCHED_START_DATE,
        yearto:Int? = SearchConfig.SEARCHED_END_DATE
    ) = apiService.getMovies(
        query = query,
        display = display,
        adualt = false,
        language = "ko-kr",
        page = 1)

    fun saveFavorite(movie: Movie) {
        database
            .favoriteDao()
            .insert(movie)
    }

    fun deleteFavorite(movie: Movie) {
        movie.title?.let {
            database
                .favoriteDao()
                .delete(it)
        }
    }
}