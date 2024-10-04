package com.tapascodev.inspector.vehicles.ui.search

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tapascodev.inspector.base.ui.showKeyboard
import com.tapascodev.inspector.base.ui.snackbar
import com.tapascodev.inspector.base.ui.visible
import com.tapascodev.inspector.databinding.FragmentSearchVehicleBinding
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.ui.VehiclesAdapter
import com.tapascodev.inspector.vehicles.ui.VehiclesFragmentDirections
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class SearchVehicleFragment : Fragment() {

    lateinit var binding: FragmentSearchVehicleBinding
    private val viewModel: SearchVehicleViewModel by viewModels()
    private lateinit var vehiclesAdapter: SearchVehicleAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(this::binding.isInitialized)
            return binding.root

        binding = FragmentSearchVehicleBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        setupObserver()

    }

    private fun setupObserver() {
        binding.apply {
            searchVehicles.requestFocus()
            searchVehicles.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchVehicles.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val searchText = newText?.uppercase(Locale.ROOT)
                    if(searchText != null) {
                        viewModel.setQuery(searchText)
                    }
                    return false
                }
            })

            //vehicles list
            viewModel.vehicles.observe(viewLifecycleOwner) {
                linearProgressSearchVehicles.visible(it is Resource.Loading)
                when(it){
                    is Resource.Failure -> {
                        requireView().snackbar(it.errorBody.toString())
                    }
                    Resource.Loading -> {}
                    is Resource.Success -> {
                        vehiclesAdapter.updateList(it.value)
                    }
                }
            }

        }
    }

    fun initUI() {
        vehiclesAdapter = SearchVehicleAdapter { vehicle ->
            findNavController().navigate(VehiclesFragmentDirections.actionVehiclesFragmentToVehicleAddEditFragment(vehicle.id))
        }
        binding.rvSearchVehicles.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = vehiclesAdapter
        }
    }
}