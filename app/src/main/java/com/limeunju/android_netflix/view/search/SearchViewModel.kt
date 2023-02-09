package com.limeunju.android_netflix.view.search

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.limeunju.android_netflix.data.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
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

    val searchMovies =
        snapshotFlow { inputQuery }
            .flatMapLatest {
                movieUseCase.getMovies(inputQuery).cachedIn(viewModelScope)
            }

}