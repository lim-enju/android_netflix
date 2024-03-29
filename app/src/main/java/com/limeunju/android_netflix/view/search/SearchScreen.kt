package com.limeunju.android_netflix.view.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Star
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.limeunju.android_netflix.R
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.view.AppBar
import com.limeunju.android_netflix.view.navigation.NavScreen

@Composable
fun SearchScreen(viewmodel: SearchViewModel = hiltViewModel(), onMovieClick: (NavScreen, Movie?) -> Unit) {
    //collectAsLazyPagingItems: PagingData의 flow collect
    val movies = viewmodel.searchMovies.collectAsLazyPagingItems()
    val favorites = viewmodel.favorites.collectAsState().value
    val query = viewmodel.inputQuery

    SearchScreen(
        onMovieClick = onMovieClick,
        movies = movies,
        favorites = favorites,
        query = query,
        onValueChange = { str -> viewmodel.setQuery(str) }
    )
}

@Composable
fun SearchScreen(
    onMovieClick: (NavScreen, Movie?) -> Unit,
    movies: LazyPagingItems<Movie>,
    favorites: Map<Int, Movie>,
    query: String,
    onValueChange: (String) -> Unit
) {
    //collectAsLazyPagingItems: PagingData의 flow collect

    Scaffold(
        topBar = { SearchAppBar() }
    ) { paddingValues ->
        Column() {
            SearchBar(
                paddingValues = paddingValues,
                query = query,
                onValueChange = { str -> onValueChange(str) }
            )
            SearchedScreen(
                movie = movies,
                favorites = favorites,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Composable
fun SearchAppBar(
){
    AppBar(
        title = {Text("")},
        navigationIcon = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(Icons.Filled.ArrowBack, null)
            }
        }
    )
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    query: String,
    onValueChange :(String)->Unit,
    paddingValues: PaddingValues
) {
    TextField(
        value = query,
        onValueChange = onValueChange,
        singleLine = true,
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp),
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
    )
}

@Composable
fun SearchedScreen(
    movie: LazyPagingItems<Movie>,
    favorites: Map<Int, Movie>,
    onMovieClick: (NavScreen, Movie?) -> Unit,
){
    when(movie.loadState.refresh){
        LoadState.Loading -> {}
        is LoadState.Error -> {}
        else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
            ){
                items(movie.itemCount)
                { index ->
                    movie[index]?.let {
                        SearchedItem(it, favorites.keys.contains(it.id), onMovieClick)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchedItem(
    movie: Movie,
    isFavorite: Boolean,
    onMovieClick: (NavScreen, Movie?) -> Unit,
    modifier: Modifier = Modifier,
    viewmodel: SearchViewModel = hiltViewModel())
{
    Card (
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .clickable { onMovieClick(NavScreen.Detail, movie) }
    ){
        Box(modifier =
        modifier
            .height(180.dp)
            .fillMaxWidth()
        ){
            AsyncImage(
                model = movie.posterPath,
                contentDescription = null,
                modifier =
                modifier
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