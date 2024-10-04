package com.tapascodev.inspector.vehicles.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapascodev.inspector.R
import com.tapascodev.inspector.base.ui.snackbar
import com.tapascodev.inspector.base.ui.visible
import com.tapascodev.inspector.databinding.FragmentVehiclesBinding
import com.tapascodev.inspector.home.ui.HomeActivity
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VehiclesFragment : Fragment () {

    lateinit var binding: FragmentVehiclesBinding
    private lateinit var viewModel: VehicleViewModel
    private lateinit var vehiclesAdapter: VehiclesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(this::binding.isInitialized)
            return binding.root

        binding = FragmentVehiclesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[VehicleViewModel::class.java]
        initUI()
        initRecyclerView()
        observer()
    }

    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.vehicles.collect{

                    when(it){
                        is Resource.Failure -> {
                            binding.linearProgressVehicles.visible(false)
                            requireView().snackbar(it.errorBody.toString())
                        }
                        Resource.Loading -> {
                            binding.linearProgressVehicles.visible(true)
                        }
                        is Resource.Success -> {
                            binding.linearProgressVehicles.visible(false)
                            vehiclesAdapter.updateList(it.value)
                        }
                    }
                }
            }
        }
    }

    private fun initRecyclerView() {
        vehiclesAdapter = VehiclesAdapter (
            list = emptyList(),
            onItemClicked = { vehicle -> findNavController().navigate(VehiclesFragmentDirections.actionVehiclesFragmentToVehicleAddEditFragment(vehicle.id))  },
            onDeleteItemClicked = { position -> deleteItem(position) }
        )

        binding.rvVehicles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = vehiclesAdapter
        }
    }

    private fun deleteItem(position: Int) {
        println(position)
    }

    private fun initUI() {

        (activity as HomeActivity).supportActionBar?.title = getString(R.string.vehicles)

        binding.apply {

            toolbarSearchVehicles.setOnClickListener {
                findNavController().navigate(VehiclesFragmentDirections.actionVehiclesFragmentToSearchVehicleFragment())
            }

            floatButtonVehicles.setOnClickListener {
                findNavController().navigate(VehiclesFragmentDirections.actionVehiclesFragmentToVehicleAddEditFragment(null))
            }

        }

    }
}