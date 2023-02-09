package com.limeunju.android_netflix.data.values

import androidx.paging.PagingSource

fun PagingSource.LoadParams<Int>.prevKey() =
    (key?.coerceAtLeast(0) ?: 0).takeIf { it > 0 }?.minus(loadSize)?.coerceAtLeast(0)

fun PagingSource.LoadParams<Int>.nextKey(total: Int) =
    (key?.coerceAtLeast(0) ?: 0).plus(loadSize).takeIf { it <= total }