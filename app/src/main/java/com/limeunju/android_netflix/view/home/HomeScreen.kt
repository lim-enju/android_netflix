package com.limeunju.android_netflix.view.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.view.AppBar
import com.limeunju.android_netflix.view.navigation.NavScreen

@Composable
fun HomeScreen(
    selectItem: (NavScreen, Movie?) -> Unit
){
    Column {
        AppBar(
            title = {Text("")},
            actions = {
                IconButton(onClick = {
                    selectItem(NavScreen.Search, null)
                }){
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
        )
        MovieList(selectItem)
    }
}

@Composable
fun MovieList(selectItem: (NavScreen, Movie?) -> Unit, viewmodel: HomeViewModel = hiltViewModel()) {
    val movies = remember { viewmodel.recomMovies }

    val scrollState = rememberScrollState()
    Column (
        modifier =
        Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .verticalScroll(scrollState)
        ){
        movies.keys.toList()
            .forEach {
                MovieItem(it, selectItem)
            }
    }
}

@Composable
fun MovieItem(query: String, selectItem: (NavScreen, Movie?) -> Unit, viewmodel: HomeViewModel = hiltViewModel()){
    val movie: LazyPagingItems<Movie> = viewmodel.recomMovies[query]?.collectAsLazyPagingItems()?:return
    val favorite = viewmodel.favorites.collectAsState()
    Log.d("EJLIM", "recomposable...")

    Column {
        Text("<$query> 과 관련된 영화")
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ){
            itemsIndexed(movie) { index, item ->
                item?.let {
                    MovieImage(item, favorite.value.keys.contains(item.title), selectItem)
                }
            }
        }
    }
}

@Composable
fun MovieImage(
    movie: Movie,
    isFavorite: Boolean,
    selectItem: (NavScreen, Movie?) -> Unit,
    viewmodel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
){
    Card (
        elevation = 5.dp,
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ){
        Box(
            modifier =
                modifier.height(200.dp)
                    .clickable { selectItem(NavScreen.Detail, movie) }
        ){
            AsyncImage(
                model = movie.image,
                contentDescription = null,
                modifier =
                    modifier
                        .height(200.dp)
                        .width(100.dp)
                        .fillMaxWidth()
                        .fillMaxHeight()
            )
            IconButton(onClick = {
                if(isFavorite) viewmodel.deleteFavorite(movie)
                else viewmodel.saveFavorite(movie)
            }) {
                Icon(
                    modifier =
                    Modifier
                        .align(Alignment.TopEnd)
                        .width(30.dp)
                        .height(30.dp),
                    imageVector = Icons.Outlined.Star,
                    contentDescription = null,
                    tint = if(isFavorite) Color.Yellow else Color.Black
                )
            }
        }
    }
}

