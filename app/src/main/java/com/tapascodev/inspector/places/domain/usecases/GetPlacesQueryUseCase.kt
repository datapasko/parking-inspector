package com.tapascodev.inspector.places.domain.usecases

import com.tapascodev.inspector.places.domain.PlaceRepository
import javax.inject.Inject

class GetPlacesQueryUseCase@Inject constructor(
    private val repository: PlaceRepository
) {
    suspend operator fun invoke(query:String) = repository.getPlacesQuery(query)
}