package com.tapascodev.inspector.vehicles.domain.usecases

import com.tapascodev.inspector.vehicles.domain.VehicleRepository
import javax.inject.Inject

class GetByPlateVehiclesUseCase  @Inject constructor(
    val repository: VehicleRepository
) {
    suspend operator fun invoke(plate: String) = repository.getByPlate(plate)
}