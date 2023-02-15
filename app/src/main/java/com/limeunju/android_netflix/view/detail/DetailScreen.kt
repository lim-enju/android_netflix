package com.limeunju.android_netflix.view.detail

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.limeunju.android_netflix.data.model.response.Movie

@Composable
fun DetailScreen(title: String, viewmodel: DetailViewModel = hiltViewModel()){
    val movie: Movie? by viewmodel.movie.collectAsState(null)

    LaunchedEffect(key1 = title) {
        viewmodel.fetchMovieDetailByTitle(title)
    }
    Log.d("EJLIM", "movie ${movie?.link}")

    val webViewState =
        rememberWebViewState(
            url = movie?.link?:"",
            additionalHttpHeaders = emptyMap()
        )

    movie?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()) {
            MovieInfo(it)
            WebView(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
                state = webViewState)
        }
    }
}

@Composable
fun MovieInfo(movie: Movie){
    Row (
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
            .padding(all = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        AsyncImage(
            model = movie.image,
            contentDescription = null,
            modifier =
            Modifier
                .height(200.dp)
                .width(100.dp)
                .fillMaxWidth()
                .fillMaxHeight()
        )

        Column (){
            Text(text = movie.title?:"")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movie.subtitle?:"")
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = movie.pubDate?:"")
        }
    }
}