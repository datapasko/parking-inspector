package com.tapascodev.inspector.vehicles.domain

import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.model.Vehicle

interface VehicleRepository {
    suspend fun getVehicles(result: (Resource<List<Vehicle>>) -> Unit)
    suspend fun getByPlate(plate:String, result: (Resource<List<Vehicle>>) -> Unit)
}