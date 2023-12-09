package com.nutricatch.dev.views.navigation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.nutricatch.dev.R
import com.nutricatch.dev.databinding.ActivityHomeBinding
import com.nutricatch.dev.views.navigation.camera.CameraActivity

class HomeActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home,
            R.id.navigation_recipe,
            R.id.navigation_camera,
            R.id.navigation_history,
            R.id.navigation_setting
        ).build()

        setSupportActionBar(binding.myToolbar)
        binding.myToolbar.visibility = View.GONE
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, navDestination, _ ->
            binding.navView.isVisible = appBarConfiguration.isTopLevelDestination(navDestination)
                .also { binding.fabScan.isVisible = it }
        }

        binding.fabScan.setOnClickListener(this)

        binding.navView.menu.getItem(2).isEnabled = false

    }

    override fun onClick(v: View) {
        when (v) {
            binding.fabScan -> {
                startActivity(Intent(this, CameraActivity::class.java))
            }
        }
    }
}