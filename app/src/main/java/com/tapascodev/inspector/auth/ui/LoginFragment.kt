package com.tapascodev.inspector.auth.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.tapascodev.inspector.R
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.base.ui.handleError
import com.tapascodev.inspector.base.ui.hideKeyboard
import com.tapascodev.inspector.base.ui.setTitle
import com.tapascodev.inspector.base.ui.startNewActivity
import com.tapascodev.inspector.base.ui.visible
import com.tapascodev.inspector.databinding.FragmentLoginBinding
import com.tapascodev.inspector.home.ui.HomeActivity
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(
    FragmentLoginBinding::inflate
) {

    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {

        setTitle("Login")

        binding.apply {
            btnLogin.setOnClickListener { login() }
            tvRegister.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_registerFragment) }
            tvResetPassword.setOnClickListener { findNavController().navigate(R.id.action_loginFragment_to_resetPasswordFragment) }
        }

    }

    private fun login() {
        val email = binding.inputEmail.editText?.text?.toString()!!.trim()
        val password = binding.inputPassword.editText?.text?.toString()!!.trim()
        viewModel.login(email, password)
        hideKeyboard()
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect{
                    binding.pbLogin.visible(it is Resource.Loading)
                    when(it){
                        is Resource.Failure -> {
                            handleError(it)
                        }
                        is Resource.Success -> {
                            startNewActivity(HomeActivity::class.java)
                        }
                        Resource.Loading -> binding.pbLogin.visible(true)
                    }
                }

            }
        }
    }

}