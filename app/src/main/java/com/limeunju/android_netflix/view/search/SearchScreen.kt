package com.limeunju.android_netflix.view.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.lazy.LazyColumn
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
import com.limeunju.android_netflix.R
import com.limeunju.android_netflix.view.AppBar
import com.limeunju.android_netflix.view.home.HomeViewModel
import com.limeunju.android_netflix.view.home.MovieList
import com.limeunju.android_netflix.view.navigation.HomeNavItem

@Composable
fun SearchScreen() {
    Scaffold(
        topBar = { SearchAppBar() }
    ) { paddingValues ->
        SearchBar(
            paddingValues = paddingValues
        )
    }

}

@Composable
fun SearchAppBar(){
    AppBar(
        title = {Text("")},
        actions = {
            IconButton(onClick = {

            }){
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
        },
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
fun SearchedItem(modifier: Modifier){
    Row(modifier = modifier) {

    }
}