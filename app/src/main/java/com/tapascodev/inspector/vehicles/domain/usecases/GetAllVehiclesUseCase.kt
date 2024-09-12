package com.tapascodev.inspector.vehicles.domain.usecases

import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import com.tapascodev.inspector.vehicles.domain.VehicleRepository
import javax.inject.Inject

class GetAllVehiclesUseCase @Inject constructor(
    val repository: VehicleRepository
) {
    suspend operator fun invoke(result: (Resource<List<Vehicle>>) -> Unit) = repository.getVehicles(result)
}