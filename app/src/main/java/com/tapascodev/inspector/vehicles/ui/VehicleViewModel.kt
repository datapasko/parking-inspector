package com.tapascodev.inspector.vehicles.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import com.tapascodev.inspector.vehicles.domain.usecases.GetAllVehiclesUseCase
import com.tapascodev.inspector.vehicles.domain.usecases.GetByPlateVehiclesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class VehicleViewModel @Inject constructor(
    private val getAllVehiclesUseCase: GetAllVehiclesUseCase,
    private val getByPlateVehiclesUseCase: GetByPlateVehiclesUseCase
) : ViewModel() {

    private val _vehicles = MutableStateFlow<Resource<List<Vehicle>>>(Resource.Loading)
    val vehicles: StateFlow<Resource<List<Vehicle>>> = _vehicles

    fun getVehicles() {
        viewModelScope.launch {
            getAllVehiclesUseCase {
                _vehicles.value = it
            }
        }
    }

    fun getVehiclesQuery(query: String) {
        viewModelScope.launch {
            getByPlateVehiclesUseCase.invoke(query) {
                _vehicles.value = it
            }
        }
    }

}