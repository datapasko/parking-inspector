package com.tapascodev.inspector.vehicles.domain

import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.model.Vehicle

interface VehicleRepository {
    suspend fun getVehicles(): Resource<List<Vehicle>>
    suspend fun getByPlate(plate:String): Resource<List<Vehicle>>
    suspend fun getVehicle(id:String): Resource<Vehicle>
    suspend fun addVehicle(vehicle: Vehicle): Resource<Boolean>
    suspend fun updateVehicle(vehicle: Vehicle): Resource<Boolean>
}