package com.limeunju.android_netflix.view.detail

import android.util.Log
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.limeunju.android_netflix.data.model.response.Movie

@Composable
fun DetailScreen(title: String, viewmodel: DetailViewModel = hiltViewModel()){
    val movie: Movie? by viewmodel.movie.collectAsState(null)

    LaunchedEffect(key1 = title) {
        viewmodel.fetchMovieDetailByTitle(title)
    }

    // Adding a WebView inside AndroidView
    // with layout as full screen
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            movie?.link?.let { loadUrl(it) }
        }
    }, update = {webview ->
        movie?.link?.let { url -> webview.loadUrl(url) }
    })
}