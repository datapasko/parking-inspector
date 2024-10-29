package com.tapascodev.inspector.home.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.tapascodev.inspector.R
import com.tapascodev.inspector.auth.ui.AuthActivity
import com.tapascodev.inspector.base.ui.snackbar
import com.tapascodev.inspector.base.ui.startNewActivity
import com.tapascodev.inspector.databinding.ActivityHomeBinding
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initNavigation()
        initObserver()
    }

    private fun initObserver() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.stateLogout.collect{
                    when(it) {
                        is Resource.Failure -> window.decorView.rootView.snackbar(it.errorBody.toString())
                        Resource.Loading -> {}
                        is Resource.Success -> {
                            startNewActivity(AuthActivity::class.java)
                            finish()
                        }
                        null -> {}
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.settings ->{
                //findNavController(this).navigate()
                true
            }

            R.id.logout -> {
                viewModel.logout()
                true

            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.cvHomeFragment) as NavHostFragment
        navController = navHost.navController
        binding.bottomNavigationHome.setupWithNavController(navController)
    }
}