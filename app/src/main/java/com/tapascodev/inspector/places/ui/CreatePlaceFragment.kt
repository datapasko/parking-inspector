package com.tapascodev.inspector.places.ui

import android.os.Bundle
import android.view.View
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.databinding.FragmentCreatePlaceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreatePlaceFragment : BaseFragment<FragmentCreatePlaceBinding> (
    FragmentCreatePlaceBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {

    }
}