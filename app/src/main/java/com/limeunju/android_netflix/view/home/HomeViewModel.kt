package com.limeunju.android_netflix.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.limeunju.android_netflix.common.SearchConfig.MOVIE_KEYWORD
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.data.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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
    init {
        MOVIE_KEYWORD
            .forEach {
                recomMovies[it] = movieUseCase.getMovies(it).cachedIn(viewModelScope)
            }
    }

    fun saveFavorite(name: String){
        viewModelScope.launch (Dispatchers.IO){
            movieUseCase.saveFavorite(name)
        }
    }

    fun deleteFavorite(name: String){
        viewModelScope.launch (Dispatchers.IO){
            movieUseCase.deleteFavorite(name)
        }
    }

}
