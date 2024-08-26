package com.tapascodev.inspector.places.ui

import android.os.Bundle
import android.view.View
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.databinding.FragmentEditPlaceBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditPlaceFragment : BaseFragment<FragmentEditPlaceBinding> (
    FragmentEditPlaceBinding::inflate
) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {

    }
}