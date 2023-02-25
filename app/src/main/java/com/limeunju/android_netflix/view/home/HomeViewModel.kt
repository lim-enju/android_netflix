package com.limeunju.android_netflix.view.home

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.limeunju.android_netflix.common.SearchConfig.MAIN_MOVIE_KEYWORD
import com.limeunju.android_netflix.common.SearchConfig.MOVIE_KEYWORD
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.data.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
): ViewModel() {
    //key: query
    //value: 검색된 영화
    val recomMovies = mutableMapOf<String, Flow<PagingData<Movie>>>()
    val favorites = movieUseCase.favorites
    val mainMovie = flow {
        val movie = movieUseCase.getMovie(MAIN_MOVIE_KEYWORD)
        Log.d("EJLIM", "flow ${movie?.title} ${movie?.image}")
        emit(movie)
    }

    init {
        MOVIE_KEYWORD
            .forEach {
                recomMovies[it] = movieUseCase.getMoviesPager(it).cachedIn(viewModelScope)
            }
    }

    fun saveFavorite(movie: Movie){
        viewModelScope.launch (Dispatchers.IO){
            movieUseCase.saveFavorite(movie)
        }
    }

    fun deleteFavorite(movie: Movie){
        viewModelScope.launch (Dispatchers.IO){
            movieUseCase.deleteFavorite(movie)
        }
    }

}
