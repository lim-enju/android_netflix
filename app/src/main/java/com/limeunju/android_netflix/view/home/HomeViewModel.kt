package com.limeunju.android_netflix.view.home

import android.util.Log
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.limeunju.android_netflix.data.model.response.Items
import com.limeunju.android_netflix.data.model.response.MovieResponse
import com.limeunju.android_netflix.data.repository.MovieRepository
import com.limeunju.android_netflix.data.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
): ViewModel() {

    fun getMovies(list: SnapshotStateList<Items>, query: String) = viewModelScope.launch (Dispatchers.IO){
        val movies = movieUseCase.searchMovie(query)?.items
        list.clear()
        list.addAll(movies!!)
    }

}