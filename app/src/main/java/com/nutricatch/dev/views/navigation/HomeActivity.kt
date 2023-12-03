package com.nutricatch.dev.views.navigation

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.nutricatch.dev.R
import com.nutricatch.dev.databinding.ActivityHomeBinding
import com.nutricatch.dev.views.camera.CameraActivity
import com.nutricatch.dev.views.navigation.history.HistoryFragment
import com.nutricatch.dev.views.navigation.home.HomeFragment
import com.nutricatch.dev.views.navigation.settings.SettingsFragment

class HomeActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_history, R.id.navigation_setting
            )
        )

        setSupportActionBar(binding.myToolbar)
        binding.myToolbar.visibility = View.GONE
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        binding.fabScan.setOnClickListener(this)

        navView.setOnItemSelectedListener(this)

    }

//    override fun onClick(v: View) {
//        when (v) {
//            binding.fabScan -> {
//                startActivity(Intent(this, CameraActivity::class.java))
//            }
//        }
//    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()

        when (item.itemId) {
            R.id.navigation_home -> {
                transaction.replace(R.id.container, HomeFragment(), HomeFragment::class.java.name)
            }

            R.id.navigation_camera -> {
                /// todo handle navigate activity camera
                startActivity(Intent(this, CameraActivity::class.java))
                return false
            }

            R.id.navigation_history -> {
                transaction.replace(
                    R.id.container,
                    HistoryFragment(),
                    HistoryFragment::class.java.name
                )
            }

            R.id.navigation_setting -> {
                transaction.replace(
                    R.id.container,
                    SettingsFragment(),
                    SettingsFragment::class.java.name
                )
            }
        }
        transaction.commit()
        return true
    }
}