package com.limeunju.android_netflix.view.home

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemsIndexed
import coil.compose.AsyncImage
import com.limeunju.android_netflix.common.nanum
import com.limeunju.android_netflix.data.model.response.Movie
import com.limeunju.android_netflix.view.AppBar
import com.limeunju.android_netflix.view.navigation.NavScreen
import kotlinx.coroutines.flow.Flow

@Composable
fun HomeScreen(
    onMovieClick: (NavScreen, Movie?) -> Unit,
    onSearchClick: (NavScreen) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
){
    val mainMovie = viewModel.mainMovie.collectAsState(initial = null).value
    val recommMovies = viewModel.recomMovies
    val favorites = viewModel.favorites.collectAsState().value

    Log.d("EJLIM", "home screen reload ${mainMovie?.title}")

    HomeScreen(
        onMovieClick = onMovieClick,
        onSearchClick = onSearchClick,
        mainMovie = mainMovie,
        recommMovies = recommMovies,
        favorites = favorites
    )

}

@Composable
fun HomeScreen(
    onMovieClick: (NavScreen, Movie?) -> Unit,
    onSearchClick: (NavScreen) -> Unit,
    mainMovie: Movie?,
    recommMovies: Map<String, Flow<PagingData<Movie>>>,
    favorites: Map<Int, Movie>
){
    Column {
        AppBar(
            title = { Text("") },
            actions = {
                IconButton(onClick = {
                    onSearchClick(NavScreen.Search)
                }){
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
        )

        val scrollState = rememberScrollState()
        Column (
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .verticalScroll(scrollState)
        ){
            MainMovie(mainMovie)
            MovieList(onMovieClick, recommMovies, favorites)
        }

    }
}

@Composable
fun MainMovie(mainMovie: Movie?){
    mainMovie?.let { movie ->
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(LocalConfiguration.current.screenWidthDp.dp)
            ) {
                Log.d("TAG", "MovieImage: ${movie.posterPath}")

                AsyncImage(
                    model = movie.posterPath,
                    contentDescription = "",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                )
                Text(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = movie.title?:"",
                    fontSize = 60.sp,
                    fontFamily = nanum,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

@Composable
fun MovieList(
    onMovieClick: (NavScreen, Movie?) -> Unit,
    movies: Map<String, Flow<PagingData<Movie>>>,
    favorites: Map<Int, Movie>
) {
    Log.d("EJLIM", "home screen reload ${movies.size}")
    movies.forEach { entry ->
        MovieItem(
            onMovieClick = onMovieClick,
            movies = entry,
            favorites = favorites
        )
    }

}

@Composable
fun MovieItem(
    onMovieClick: (NavScreen, Movie?) -> Unit,
    movies: Map.Entry<String, Flow<PagingData<Movie>>>,
    favorites: Map<Int, Movie>
){
    val query = movies.key
    val movie: LazyPagingItems<Movie> = movies.value.collectAsLazyPagingItems()

    Column {
        Text(
            modifier = Modifier.padding(0.dp, 32.dp, 0.dp, 16.dp),
            text="${query}와 비슷한 콘텐츠",
            fontSize = 20.sp,
            fontFamily = nanum,
            fontWeight = FontWeight.Bold
        )
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ){
            itemsIndexed(movie) { index, item ->
                item?.let {
                    MovieImage(item, favorites.keys.contains(item.id), onMovieClick)
                }
            }
        }
    }
}

@Composable
fun MovieImage(
    movie: Movie,
    isFavorite: Boolean,
    onMovieClick: (NavScreen, Movie?) -> Unit,
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
            modifier
                .width(100.dp)
                .clickable { onMovieClick(NavScreen.Detail, movie) }
        ){
            AsyncImage(
                model = movie.posterPath,
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier =
                modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
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

