package com.tapascodev.inspector.places.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.tapascodev.inspector.R
import com.tapascodev.inspector.base.ui.snackbar
import com.tapascodev.inspector.base.ui.visible
import com.tapascodev.inspector.databinding.FragmentPlacesBinding
import com.tapascodev.inspector.home.ui.HomeActivity
import com.tapascodev.inspector.network.domain.Resource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class PlacesFragment : Fragment() {

    lateinit var binding: FragmentPlacesBinding

    private val placesViewModel: PlacesViewModel by viewModels()

    private lateinit var placeAdapter: PlaceAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if(this::binding.isInitialized)
            return binding.root

        binding = FragmentPlacesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initRecyclerView()
        observer()
    }

    private fun initUI() {

        (activity as HomeActivity).supportActionBar?.title = getString(R.string.places)
        binding.apply {

            searchView.clearFocus()

            searchView.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    searchView.clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    val searchText = newText?.lowercase(Locale.ROOT)
                    if (searchText != null) {
                        placesViewModel.setQuery(searchText)
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
            val marginItem = PlaceDecoration()
            addItemDecoration(marginItem)
        }
    }

    private fun observer() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                placesViewModel.places.collect {
                    binding.lpPLaces.visible(it is Resource.Loading)
                    when(it) {
                        is Resource.Failure -> {
                            requireView().snackbar(it.errorBody.toString())
                        }
                        Resource.Loading -> {}
                        is Resource.Success -> {
                            placeAdapter.updateList(it.value)
                        }
                    }

                }
            }
        }

        placesViewModel.stateQuery.observe(viewLifecycleOwner) {
            binding.lpPLaces.visible(it is Resource.Loading)

            when (it) {
                is Resource.Failure -> {}
                Resource.Loading -> {}
                is Resource.Success -> {
                    placeAdapter.updateList(it.value)
                }
            }

        }
    }
}