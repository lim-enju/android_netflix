package com.limeunju.android_netflix.view.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.limeunju.android_netflix.R
import com.limeunju.android_netflix.view.home.HomeScreen
import com.limeunju.android_netflix.view.search.SearchScreen

@Composable
fun NavGraph( modifier: Modifier = Modifier, navController: NavHostController){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = BottomNavItem.Home.screenRoute
    ) {
        composable(BottomNavItem.Home.screenRoute) {
            HomeScreen(){ screen ->
                when(screen){
                    HomeNavItem.Search -> navController.navigate(HomeNavItem.Search.screenRoute)
                }
            }
        }
        composable(BottomNavItem.Game.screenRoute) {
            HomeScreen(){}
        }
        composable(BottomNavItem.Feed.screenRoute) {
            HomeScreen(){}
        }
        composable(
            route = HomeNavItem.Search.screenRoute
        ){
            SearchScreen()
        }
    }
}

@Composable
fun BottomBarNavigation(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: return

    val items = listOf<BottomNavItem>(
        BottomNavItem.Home,
        BottomNavItem.Game,
        BottomNavItem.Feed,
    )


    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color(0xFF3F414E)
    ) {
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title),
                        modifier = Modifier
                            .width(26.dp)
                            .height(26.dp)
                    )
                },
                selected = currentRoute == item.screenRoute,
                onClick = {
                    navController.navigate(item.screenRoute) {
                        navController.graph.startDestinationRoute?.let {
                            popUpTo(it) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = { Text(stringResource(id = item.title), fontSize = 9.sp) },
            )
        }
    }
}