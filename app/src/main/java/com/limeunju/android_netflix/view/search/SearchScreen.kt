package com.limeunju.android_netflix.view.search

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
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.limeunju.android_netflix.R
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.view.AppBar

@Composable
fun SearchScreen(viewmodel: SearchViewModel = hiltViewModel()) {
    //collectAsLazyPagingItems: PagingData의 flow collect
    val movies = viewmodel.searchMovies.collectAsLazyPagingItems()
    val favorites = viewmodel.favorites.collectAsState()

    Scaffold(
        topBar = { SearchAppBar() }
    ) { paddingValues ->
        Column() {
            SearchBar(
                paddingValues = paddingValues
            )
            SearchedScreen(movies, favorites.value)
        }
    }

}

@Composable
fun SearchAppBar(){
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
    paddingValues: PaddingValues,
    viewmodel: SearchViewModel = hiltViewModel()
) {

    TextField(
        value = viewmodel.inputQuery,
        onValueChange = viewmodel::setQuery,
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
    favorites: List<String>,
    viewmodel: SearchViewModel = hiltViewModel(),
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
                        SearchedItem(it, favorites.contains(it.title))
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
    viewmodel:SearchViewModel = hiltViewModel(),
    modifier: Modifier = Modifier)
{
    Card (
        modifier = modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ){
        Box(modifier =
        modifier
            .height(180.dp)
            .fillMaxWidth()
        ){
            AsyncImage(
                model = movie.image,
                contentDescription = null,
                modifier =
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
            IconButton(onClick = {
                movie.title?.let {title ->
                    if(isFavorite) viewmodel.deleteFavorite(title)
                    else viewmodel.saveFavorite(title)
                }
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