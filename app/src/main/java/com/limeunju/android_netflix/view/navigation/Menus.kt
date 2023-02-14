package com.limeunju.android_netflix.view.navigation

import android.os.Bundle
import androidx.navigation.NavType
import com.google.gson.Gson
import com.limeunju.android_netflix.R
import com.limeunju.android_netflix.data.model.response.Movie
import javax.annotation.concurrent.Immutable

private const val HOME = "HOME"
private const val GAME = "GAME"
private const val FEED = "FEED"
private const val SEARCH = "SEARCH"

sealed class BottomNavItem(
    val title: Int, val icon: Int, val screenRoute: String
) {
    object Home : BottomNavItem(R.string.menu_home, R.drawable.baseline_home_black_20, HOME)
    object Game : BottomNavItem(R.string.menu_game, R.drawable.baseline_sports_esports_black_20, GAME)
    object Feed : BottomNavItem(R.string.menu_feed, R.drawable.baseline_feed_black_20, FEED)


}

sealed class NavScreen(val screenRoute: String){
    object Search: NavScreen("Search")
    object Favorite: NavScreen("Favorite")
    object Detail: NavScreen("Detail"){
        const val routeWithArgument: String = "Detail/{title}"
        const val title: String = "title"
    }
}