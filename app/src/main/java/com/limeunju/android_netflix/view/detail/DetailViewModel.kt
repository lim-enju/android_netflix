package com.limeunju.android_netflix.view.detail

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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
): ViewModel() {
    private val _movie = MutableStateFlow<Movie?>(null)
    val movie = _movie.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), null)

    sealed class DetailError{
        class NotFoundError:DetailError()
        class NetworkError:DetailError()
    }

    fun fetchMovieDetailByTitle(title: String){
        viewModelScope.launch (Dispatchers.IO){
            val movie = movieUseCase.getMovie(title)
            if(movie != null){
                _movie.emit(movie)
            }
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
