package com.tapascodev.inspector.places.ui

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tapascodev.inspector.R
import com.tapascodev.inspector.base.ui.BaseFragment
import com.tapascodev.inspector.databinding.FragmentPlacesBinding
import com.tapascodev.inspector.home.ui.HomeActivity
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
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

        (activity as HomeActivity).supportActionBar?.title = getString(R.string.places)
        placesViewModel.getPlaces()
        binding.apply {

            floor1.setOnClickListener {
                placesViewModel.getPlaces(1)
                toggleButtonFloor.setBackgroundColor(Color.YELLOW)
            }
            floor2.setOnClickListener { placesViewModel.getPlaces(2) }
            floor3.setOnClickListener { placesViewModel.getPlaces(3) }
            floor4.setOnClickListener { placesViewModel.getPlaces(4) }

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