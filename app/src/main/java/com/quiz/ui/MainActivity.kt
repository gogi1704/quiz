package com.quizApp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.quizApp.R
import com.quizApp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

        val navController = navHostFragment.navController

        val bottomNav = binding.navigation
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.menu_quiz -> {
                    navController.navigate(R.id.quizFragment)
                    true
                }

                R.id.menu_news -> {
                    navController.navigate(R.id.newsFragment)
                    true
                }R.id.menu_training->{
                    navController.navigate(R.id.trainingFragment)
                true
                }

                else -> {
                    false
                }
            }
        }


    }
}