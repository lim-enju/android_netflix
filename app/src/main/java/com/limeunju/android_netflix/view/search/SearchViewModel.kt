package com.limeunju.android_netflix.view.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.data.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

//https://medium.com/androiddevelopers/effective-state-management-for-textfield-in-compose-d6e5b070fbe5
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {
    var inputQuery by mutableStateOf("")
        private set

    fun setQuery(query: String){
        this.inputQuery = query
    }

    val favorites = movieUseCase.favorites
    val searchMovies =
        snapshotFlow { inputQuery }
            .filter { it.isBlank() }
            .distinctUntilChanged()     //동일한 단어 검색 시 필터링
            .debounce(2000)     //2초 후에 데이터 흘려보내기
            .flatMapLatest {    //새로운 플로우 방출 시 이전 플로우 취소
                movieUseCase.getMoviesPager(inputQuery).cachedIn(viewModelScope)
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