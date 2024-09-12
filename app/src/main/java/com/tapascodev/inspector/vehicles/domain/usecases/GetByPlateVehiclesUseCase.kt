package com.tapascodev.inspector.vehicles.domain.usecases

import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.vehicles.domain.VehicleRepository
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import javax.inject.Inject

class GetByPlateVehiclesUseCase  @Inject constructor(
    val repository: VehicleRepository
) {
    suspend operator fun invoke(plate: String, result: (Resource<List<Vehicle>>) -> Unit) = repository.getByPlate(plate, result)
}