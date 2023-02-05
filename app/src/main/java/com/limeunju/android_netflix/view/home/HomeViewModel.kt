package com.limeunju.android_netflix.view.home

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.limeunju.android_netflix.data.model.response.Items
import com.limeunju.android_netflix.data.model.response.MovieResponse
import com.limeunju.android_netflix.data.repository.MovieRepository
import com.limeunju.android_netflix.data.usecase.MovieUseCase
import com.limeunju.android_netflix.data.values.MOVIE_KEYWORD
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    val recomMovies = flow{
        emit(movieUseCase.searchMovie(MOVIE_KEYWORD))
    }.map { movies ->
        movies.toMutableMap().values.removeIf{ it.items.isEmpty() }
        movies
    }.flowOn(Dispatchers.IO)

}