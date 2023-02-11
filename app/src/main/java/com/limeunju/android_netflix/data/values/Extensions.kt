package com.limeunju.android_netflix.data.values

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.LazyGridScope
import androidx.compose.runtime.Composable
import androidx.paging.PagingSource
import androidx.paging.compose.LazyPagingItems

//이전 페이지 반환
fun PagingSource.LoadParams<Int>.prevKey() =
    (key?.coerceAtLeast(0) ?: 0).takeIf { it > 0 }?.minus(loadSize)?.coerceAtLeast(0)

//다음 페이지 반환
fun PagingSource.LoadParams<Int>.nextKey(total: Int) =
    (key?.coerceAtLeast(0) ?: 0).plus(loadSize).takeIf { it <= total }
