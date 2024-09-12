package com.tapascodev.inspector.vehicles.ui

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.tapascodev.inspector.R
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.databinding.FragmentVehiclesBinding

class VehiclesFragment : BaseFragment<FragmentVehiclesBinding>(
    FragmentVehiclesBinding::inflate
) {

    private val viewModel: VehicleViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        initRecyclerView()
        observer()
    }

    private fun observer() {

    }

    private fun initRecyclerView() {

    }

    private fun initUI() {

    }
}