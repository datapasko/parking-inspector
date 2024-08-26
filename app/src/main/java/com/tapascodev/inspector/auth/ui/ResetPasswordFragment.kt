package com.tapascodev.inspector.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tapascodev.inspector.R
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.databinding.FragmentResetPasswordBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment : BaseFragment<FragmentResetPasswordBinding>(
    FragmentResetPasswordBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initUI()
    }

    private fun initUI() {
    }
}