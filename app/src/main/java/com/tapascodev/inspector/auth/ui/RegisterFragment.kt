package com.tapascodev.inspector.auth.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.tapascodev.inspector.auth.domain.model.UserSignIn
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.base.ui.handleError
import com.tapascodev.inspector.base.ui.snackbar
import com.tapascodev.inspector.base.ui.startNewActivity
import com.tapascodev.inspector.base.ui.visible
import com.tapascodev.inspector.databinding.FragmentRegisterBinding
import com.tapascodev.inspector.home.ui.HomeActivity
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : BaseFragment<FragmentRegisterBinding>(
    FragmentRegisterBinding::inflate
) {


    private val viewModel by viewModels<AuthViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUI()
    }

    private fun initUI() {

        binding.apply {
            btnRegister.setOnClickListener { register() }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {

                viewModel.registerState.collect {
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

    private fun register() {
        val name = binding.inputName.editText?.text?.toString()!!.trim()
        val nickname = binding.inputNickname.editText?.text?.toString()!!.trim()
        val email = binding.inputEmail.editText?.text?.toString()!!.trim()
        val password = binding.inputPassword.editText?.text?.toString()!!.trim()

        val user = UserSignIn (
            null, name, nickname, email, password
        )

        if(user.isNotEmpty())
            viewModel.register(user)
        else
            requireView().snackbar("Can´t inputs empty")
    }
}