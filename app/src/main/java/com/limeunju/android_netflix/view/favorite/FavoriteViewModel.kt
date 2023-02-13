package com.limeunju.android_netflix.view.favorite

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
class FavoriteViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
): ViewModel() {
    val favorites = movieUseCase.favorites

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
