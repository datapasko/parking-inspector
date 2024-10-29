package com.tapascodev.inspector.home.domain

import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    val repository: HomeRepository
) {
    suspend operator fun invoke() = repository.logout()
}