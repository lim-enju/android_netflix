package com.limeunju.android_netflix.view.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import com.limeunju.android_netflix.common.darkColorScheme
import com.limeunju.android_netflix.data.model.response.Items
import com.limeunju.android_netflix.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding : FragmentHomeBinding? = null
    private val viewmodel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        val view = binding.root
        binding.composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val scrollState = rememberScrollState()
                // In Compose world
                MaterialTheme(colors = darkColorScheme) {
                    Column (
                        Modifier
                            .fillMaxSize()
                            .verticalScroll(scrollState)
                            ){
                        MovieList("해리포터")
                        MovieList("아바타")
                        MovieList("스파이더맨")
                        MovieList("모험")
                        MovieList("공룡")
                    }
                }
            }
        }
        return view
    }

    @Composable
    fun MovieList(query: String) {
        val list = remember { mutableStateListOf<Items>() }
        viewmodel.getMovies(list, query)

        Column {
            Text("<$query> 과 관련된 영화")
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ){
                itemsIndexed(list) {index, item ->
                    MovieImage(item)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
