package com.tapascodev.inspector.places.domain.usecases

import com.tapascodev.inspector.places.domain.PlaceRepository
import javax.inject.Inject

class GetAllPlacesUseCase @Inject constructor(
    val repository: PlaceRepository
) {
    suspend operator fun invoke() = repository.getPlaces()
}