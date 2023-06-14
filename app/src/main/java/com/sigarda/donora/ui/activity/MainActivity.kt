package com.sigarda.donora.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.messaging.FirebaseMessaging
import com.sigarda.donora.R
import com.sigarda.donora.data.network.models.notification.Data
import com.sigarda.donora.data.network.models.notification.NotificationModel
import com.sigarda.donora.databinding.ActivityMainBinding
import com.sigarda.donora.ui.fragment.editprofile.EditProfileFragment
import com.sigarda.donora.ui.fragment.home.HomeFragment
import com.sigarda.donora.ui.fragment.leaderboard.LeaderboardFragment
import com.sigarda.donora.ui.fragment.profile.ProfileFragment
import com.sigarda.donora.ui.fragment.schedule.ScheduleFragment
import com.sigarda.donora.ui.fragment.splash.SplashFragment
import com.sigarda.donora.ui.fragment.stock.StockFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private  val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        setupNavigation()
        navController = findNavController(R.id.hostFragment)
        setupActionBarWithNavController(navController)
        setupSmoothBottomMenu()

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {


                else -> hideBottomNav(false)
            }
        }

    }
    private fun hideBottomNav(hide: Boolean) {
        if (hide) {
            binding.navbar.visibility = View.GONE
        } else {
            binding.navbar.visibility = View.VISIBLE
        }
    }

    private fun setupSmoothBottomMenu() {
        val popupMenu = PopupMenu(this, null)
        popupMenu.inflate(R.menu.bottom_nav_menu)
        val menu = popupMenu.menu
        //binding.bottomBar.setupWithNavController(menu, navController)
        binding.bottomNavigationView.setupWithNavController( navController)
    }

    private fun setCurrentFragment(fragment: Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.hostFragment,fragment)
            commit()
        }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

        }
        return super.onOptionsItemSelected(item)
    }

    //set an active fragment programmatically

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    private fun setupNavigation() {
        // As we're inside a fragment calling `findNavController()` directly will crash the app
        // Hence, get a reference of `NavHostFragment`
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment


        // set navigation controller
        navController = navHostFragment.findNavController()

        // appbar configuration (for back button)
        val appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    fun setBottomNavigationVisibility(visibility: Int) {
        binding.navbar.visibility = visibility
    }

    fun setToolbarVisibility(isVisible: Boolean) {
        if (isVisible) supportActionBar?.show() else supportActionBar?.hide()
    }


}

