package com.limeunju.android_netflix.view.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.limeunju.android_netflix.view.navigation.BottomBarNavigation
import com.limeunju.android_netflix.view.navigation.NavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomBarNavigation(navController = navController) }
            ) { paddingValues ->
                NavGraph(
                    modifier = Modifier.padding(
                        bottom = paddingValues.calculateBottomPadding()),
                    navController = navController
                )
            }
        }
    }
}