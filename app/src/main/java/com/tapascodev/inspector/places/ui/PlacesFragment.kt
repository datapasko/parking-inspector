package com.tapascodev.inspector.places.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.marginEnd
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.databinding.FragmentPlacesBinding
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class PlacesFragment : BaseFragment<FragmentPlacesBinding> (
    FragmentPlacesBinding::inflate
){

    private val placesViewModel: PlacesViewModel by viewModels()

    private lateinit var placeAdapter: PlaceAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initUI()
        initRecyclerView()
        observer()
    }

    private fun initUI() {
        placesViewModel.getPlaces()
        binding.apply {
            cardFloorOne.setOnClickListener { placesViewModel.getPlaces(1) }
            cardFloorTwo.setOnClickListener { placesViewModel.getPlaces(2) }
            cardFloorThree.setOnClickListener { placesViewModel.getPlaces(3) }
            cardFloorFour.setOnClickListener { placesViewModel.getPlaces(4) }

            searchView.clearFocus()
            searchView.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val searchText = newText?.lowercase(Locale.ROOT)
                    if(!searchText.isNullOrEmpty()) {
                        placesViewModel.getPlacesQuery(searchText)
                    } else {
                        placesViewModel.getPlaces()
                    }
                    return false
                }
            })
        }


    }

    private fun initRecyclerView() {
        placeAdapter = PlaceAdapter()
        binding.rvPlaces.apply {
            layoutManager = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)
            adapter = placeAdapter
        }
    }

    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                placesViewModel.places.collect {
                    when(it) {
                        is Resource.Failure -> {}
                        Resource.Loading -> {}
                        is Resource.Success -> {
                            placeAdapter.updateList(it.value)
                        }
                    }

                }
            }
        }
    }

}