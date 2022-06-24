package com.adem.pokemonapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.adem.pokemonapp.R
import com.adem.pokemonapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding : ActivityMainBinding? = null
    private val binding get() = _binding!!

    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var appBarConfiguration : AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController

        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(
            navController, appBarConfiguration
        )

        menu()

        //Firebase Crashlytics
        //throw RuntimeException("Test Crash")
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    fun menu(){
        toggle = ActionBarDrawerToggle(this, binding.drawerLayout,R.string.open,R.string.close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.closeItem -> {
                    this.finish()
                }
            }
            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}