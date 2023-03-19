package com.ewida.rickmorti.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.PopupMenu
import androidx.navigation.fragment.NavHostFragment
import com.ewida.rickmorti.R
import com.ewida.rickmorti.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    var binding: ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setUpBottomNavigation()
        setContentView(binding?.root)
    }

    private fun setUpBottomNavigation() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.menuInflater.inflate(R.menu.bottom_nav_menu, popupMenu.menu)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.homeFragmentContainer) as NavHostFragment
        binding?.bottomNavView?.setupWithNavController(
            popupMenu.menu,
            navHostFragment.navController
        )

    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }
}