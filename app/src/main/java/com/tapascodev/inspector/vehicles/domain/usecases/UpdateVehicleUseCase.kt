package com.tapascodev.inspector.vehicles.domain.usecases

import com.tapascodev.inspector.vehicles.domain.VehicleRepository
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import javax.inject.Inject

class UpdateVehicleUseCase @Inject constructor(
    val repository: VehicleRepository
) {
    suspend operator fun invoke(vehicle: Vehicle) = repository.updateVehicle(vehicle)
}