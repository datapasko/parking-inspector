package com.tapascodev.inspector.places.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapascodev.inspector.base.ui.BaseViewModel
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.places.domain.model.Place
import com.tapascodev.inspector.places.domain.usecases.GetAllPlacesUseCase
import com.tapascodev.inspector.places.domain.usecases.GetPlacesQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val getAllPlacesUseCase: GetAllPlacesUseCase,
    private val getPlacesQueryUseCase: GetPlacesQueryUseCase
) : ViewModel() {

    private val _places = MutableStateFlow<Resource<List<Place>>>(Resource.Loading)
    val places: StateFlow<Resource<List<Place>>> = _places

    fun getPlaces(floor: Int = 1) {
        viewModelScope.launch {
            getAllPlacesUseCase.invoke(floor) {
                _places.value = it
            }
        }
    }

    fun getPlacesQuery(query: String) {
        viewModelScope.launch {
            getPlacesQueryUseCase.invoke(query) {
                Log.d("messi", query)
                _places.value = it
            }
        }
    }

}