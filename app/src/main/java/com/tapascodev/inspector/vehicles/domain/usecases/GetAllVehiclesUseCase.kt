package com.tapascodev.inspector.vehicles.domain.usecases

import com.tapascodev.inspector.vehicles.domain.VehicleRepository
import javax.inject.Inject

class GetAllVehiclesUseCase @Inject constructor(
    val repository: VehicleRepository
) {
    suspend operator fun invoke() = repository.getVehicles()
}