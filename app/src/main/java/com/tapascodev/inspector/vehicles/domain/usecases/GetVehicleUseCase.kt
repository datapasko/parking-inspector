package com.tapascodev.inspector.vehicles.domain.usecases

import com.tapascodev.inspector.vehicles.domain.VehicleRepository
import javax.inject.Inject

class GetVehicleUseCase  @Inject constructor(
    val repository: VehicleRepository
) {
    suspend operator fun invoke(id: String) = repository.getVehicle(id)
}