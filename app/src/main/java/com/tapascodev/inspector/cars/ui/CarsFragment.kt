package com.tapascodev.inspector.cars.ui

import android.os.Bundle
import android.view.View
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.databinding.FragmentCarsBinding

class CarsFragment : BaseFragment<FragmentCarsBinding> (
    FragmentCarsBinding::inflate
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
    }

    private fun initUI() {

    }
}