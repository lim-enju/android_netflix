package com.limeunju.android_netflix.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.limeunju.android_netflix.R
import com.limeunju.android_netflix.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var _binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(_binding.root)

        // 네비게이션들을 담는 호스트
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.my_nav_host) as NavHostFragment

        // 네비게이션 컨트롤러
        val navController = navHostFragment.navController

        // 바텀 네비게이션 뷰 와 네비게이션을 묶어준다
        NavigationUI.setupWithNavController(_binding.myBottomNav, navController)
    }
}