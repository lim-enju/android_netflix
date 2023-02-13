package com.limeunju.android_netflix.view.favorite

import com.limeunju.android_netflix.view.home.HomeViewModel

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import com.limeunju.android_netflix.view.search.SearchedItem

@Composable
fun FavoriteScreen(viewmodel: FavoriteViewModel = hiltViewModel()){
    val movies = viewmodel.favorites.collectAsState()

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ){
        movies.value.forEach {title, movie ->
            item {
                MovieImage(movie, true)
            }
        }
    }
}


@Composable
fun MovieImage(
    movie: Movie,
    isFavorite: Boolean,
    viewmodel: HomeViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
){
    Card (
        elevation = 5.dp,
        modifier = modifier
            .wrapContentHeight()
            .wrapContentWidth()
    ){
        Box(modifier = modifier.height(200.dp)){
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
                viewmodel.deleteFavorite(movie)
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

