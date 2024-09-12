package com.tapascodev.inspector.auth.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.tapascodev.inspector.R
import com.tapascodev.inspector.base.ui.startNewActivity
import com.tapascodev.inspector.base.ui.visible
import com.tapascodev.inspector.databinding.ActivityAuthBinding
import com.tapascodev.inspector.home.ui.HomeActivity
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuthBinding
    private lateinit var navController: NavController
    private val viewModel by viewModels<AuthViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityAuthBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
        initNavigation()
    }

    private fun initUI() {

        binding.apply {


        }

    }

    private fun initNavigation() {
        val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController = navHost.navController
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launch {
            viewModel.currentUser()
            viewModel.currentState.collect{
                Log.d("messi", it.toString())
                when (it) {
                    is Resource.Success -> {
                        if(it.value) startNewActivity(HomeActivity::class.java) else binding.navHostFragment.visible(true)
                        binding.pbAuth.visible(false)
                    }
                    is Resource.Failure -> { binding.navHostFragment.visible(true) }
                    Resource.Loading -> {
                        binding.pbAuth.visible(true)
                        binding.navHostFragment.visible(false)
                    }
                }
            }
        }
    }
}