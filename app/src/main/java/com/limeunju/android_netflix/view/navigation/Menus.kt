package com.limeunju.android_netflix.view.navigation

import com.limeunju.android_netflix.R

private const val HOME = "HOME"
private const val GAME = "GAME"
private const val FEED = "FEED"

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
) {
    object Home : BottomNavItem(R.string.menu_home, R.drawable.baseline_home_black_20, HOME)
    object Game : BottomNavItem(R.string.menu_game, R.drawable.baseline_sports_esports_black_20, GAME)
    object Feed : BottomNavItem(R.string.menu_feed, R.drawable.baseline_feed_black_20, FEED)
}