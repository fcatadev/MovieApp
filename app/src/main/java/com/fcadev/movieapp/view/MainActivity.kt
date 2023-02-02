package com.fcadev.movieapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.fcadev.movieapp.R
import com.fcadev.movieapp.databinding.ActivityMainBinding
import com.fcadev.movieapp.viewmodel.PopularMoviesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomBar)
        val navController = findNavController(R.id.fragmentContainerView)

        bottomNavigationView.setupWithNavController(navController)

        /*
        val navHost =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment

        val navController = navHost.navController
         */

        /*
        binding.bottomBar.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            val isVisibleBottomBar = when (destination.id) {
                R.id.homeFragmentBtn -> false
                R.id.popularFragmentBtn -> false
                R.id.favoriteFragmentBtn -> false
                else -> true
            }

            binding.bottomBar.isVisible = isVisibleBottomBar
        }
         */

        /*
        bottomNav = findViewById<BottomNavigationView>(R.id.bottomBar)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.homeFragmentBtn -> {
                    loadFragment(HomeFragment())
                }
                R.id.popularFragmentBtn -> {
                    loadFragment(PopularMoviesFragment())
                }
                R.id.favoriteFragmentBtn -> {
                    loadFragment(FavoriteMoviesFragment())
                }
            }
            return@setOnItemSelectedListener true
        }


            /*
            loadFragment(HomeFragment())


             */
         */
    }

/*
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragmentContainerView,fragment)
        transaction.commit()
    }
 */
}