package com.tapascodev.inspector.places.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.places.domain.model.Place
import com.tapascodev.inspector.places.domain.usecases.GetAllPlacesUseCase
import com.tapascodev.inspector.places.domain.usecases.GetPlacesQueryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
    private val getAllPlacesUseCase: GetAllPlacesUseCase,
    private val getPlacesQueryUseCase: GetPlacesQueryUseCase
) : ViewModel() {

    private val _places = MutableStateFlow<Resource<List<Place>>>(Resource.Loading)
    val places = _places
        .onStart { getPlaces() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            Resource.Loading
        )

    private val trigger = MutableLiveData("0")

    val stateQuery: LiveData<Resource<List<Place>>> = trigger.switchMap { query ->
        liveData {
            if(query.isNullOrEmpty()) getPlaces() else getPlacesQuery(query)
        }
    }

    fun getPlaces() {
        viewModelScope.launch {
            _places.value = Resource.Loading
            _places.value = getAllPlacesUseCase.invoke()
        }
    }

    fun getPlacesQuery(query: String) {
        viewModelScope.launch {
            _places.value = Resource.Loading
            _places.value = getPlacesQueryUseCase.invoke(query)
        }
    }

    fun setQuery(query: String) {
        trigger.value = query
    }
    
    

}