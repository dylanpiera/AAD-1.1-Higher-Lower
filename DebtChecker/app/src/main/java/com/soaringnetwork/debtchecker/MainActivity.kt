package com.soaringnetwork.debtchecker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        initNavigation()
    }

    private fun initNavigation() {
        // The NavController
        val navController = findNavController(R.id.fragment)

        // Connect the navHostFragment with the ActionBar.
        val appBarConfiguration = AppBarConfiguration(navController.graph, null) {true}
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }


}
