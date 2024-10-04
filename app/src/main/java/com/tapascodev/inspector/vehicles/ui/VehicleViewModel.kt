package com.tapascodev.inspector.vehicles.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import com.tapascodev.inspector.vehicles.domain.usecases.AddVehicleUseCase
import com.tapascodev.inspector.vehicles.domain.usecases.GetAllVehiclesUseCase
import com.tapascodev.inspector.vehicles.domain.usecases.GetVehicleUseCase
import com.tapascodev.inspector.vehicles.domain.usecases.UpdateVehicleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VehicleViewModel @Inject constructor(
    private val getAllVehiclesUseCase: GetAllVehiclesUseCase,
    private val getVehicleUseCase: GetVehicleUseCase,
    private val addVehicleUseCase: AddVehicleUseCase,
    private val updateVehicleUseCase: UpdateVehicleUseCase
) : ViewModel() {

    private val _vehicles = MutableStateFlow<Resource<List<Vehicle>>>(Resource.Loading)
    val vehicles: StateFlow<Resource<List<Vehicle>>> =  _vehicles.asStateFlow()

    private val _vehicle = MutableStateFlow<Resource<Vehicle>?>(null)
    val vehicle: StateFlow<Resource<Vehicle>?> = _vehicle.asStateFlow()

    private val _formState = MutableLiveData<Resource<Boolean>?>()
    val formState: MutableLiveData<Resource<Boolean>?> = _formState

    init {
        getVehicles()
    }

    private fun getVehicles() {
        viewModelScope.launch {
            _vehicles.value = Resource.Loading
            _vehicles.value = getAllVehiclesUseCase()
        }
    }

    fun getVehicle(id: String) {
        viewModelScope.launch {
            _vehicle.value = Resource.Loading
            _vehicle.value = getVehicleUseCase.invoke(id)
        }
    }

    fun saveVehicle(vehicle: Vehicle) {
        _formState.value = Resource.Loading
        viewModelScope.launch {
            val response = addVehicleUseCase.invoke(vehicle)

            //add vehicle to list
            if(response is Resource.Success) {
                val currentItems = (vehicles.value as? Resource.Success)?.value?.toMutableList() ?: mutableListOf()
                currentItems.add(vehicle)
                _vehicles.value = Resource.Success(currentItems.sortedBy { it.plate })
            }

            _formState.value = response
        }
    }

    fun updateVehicle(vehicle: Vehicle) {
        viewModelScope.launch {
            _formState.value = Resource.Loading
            val response = updateVehicleUseCase.invoke(vehicle)
            if(response is Resource.Success)
                getVehicles()
            _formState.value = response
        }
    }

    fun resetFormState() {
        _formState.value = null
        _vehicle.value = null
    }

}