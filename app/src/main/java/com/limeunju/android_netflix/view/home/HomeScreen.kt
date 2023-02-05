package com.limeunju.android_netflix.view.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.limeunju.android_netflix.data.model.response.Items
import com.limeunju.android_netflix.data.model.response.MovieResponse
import com.limeunju.android_netflix.view.AppBar

@Composable
fun HomeScreen(){
    Column {
        AppBar(
            title = {Text("")},
            actions = {
                IconButton(onClick = { }){
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Search",
                        tint = Color.White
                    )
                }
            }
        )
        MovieList()
    }
}

@Composable
fun MovieList(viewmodel: HomeViewModel = hiltViewModel()) {
    val movies:Map<String, MovieResponse>? by viewmodel.recomMovies.collectAsState(null)

    LazyColumn{
        movies?.forEach { (query, movies) ->
            item{
                Column {
                    Text("<$query> 과 관련된 영화")
                    LazyHorizontalGrid(
                        rows = GridCells.Fixed(1),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    ){
                        itemsIndexed(movies.items) {index, item ->
                            MovieImage(item)
                        }
                    }
                }
            }
        }
    }


}

@Composable
fun MovieImage(
    item: Items,
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
                model = item.image,
                contentDescription = null,
                modifier =
                modifier
                    .height(200.dp)
                    .width(100.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }
    }
}

