package com.limeunju.android_netflix.view.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.paging.compose.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.limeunju.android_netflix.R
import com.limeunju.android_netflix.data.model.response.Items
import com.limeunju.android_netflix.data.model.response.MovieResponse
import com.limeunju.android_netflix.view.AppBar
import com.limeunju.android_netflix.view.home.HomeViewModel
import com.limeunju.android_netflix.view.home.MovieList
import com.limeunju.android_netflix.view.navigation.HomeNavItem

@Composable
fun SearchScreen(viewmodel: SearchViewModel = hiltViewModel()) {
    //collectAsLazyPagingItems: PagingData의 flow collect
    val movies = viewmodel.searchMovies.collectAsLazyPagingItems()

    Scaffold(
        topBar = { SearchAppBar() }
    ) { paddingValues ->
        Column() {
            SearchBar(
                paddingValues = paddingValues
            )
            SearchedScreen(movies)
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
fun SearchedScreen(movies: LazyPagingItems<Items>, modifier: Modifier = Modifier){
    when(movies.loadState.refresh){
        LoadState.Loading -> {}
        is LoadState.Error -> {}
        else -> {
            SearchedMovieGrid(movies)
        }
    }
}

@Composable
fun SearchedMovieGrid(movies: LazyPagingItems<Items>){
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
    ){
        items(movies.itemCount)
        { index ->
            movies[index]?.let {
                SearchedItem(it)
            }
        }
    }
}


@Composable
fun SearchedItem(item: Items, modifier: Modifier = Modifier){
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
                model = item.image,
                contentDescription = null,
                modifier =
                modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }

}