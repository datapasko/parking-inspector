package com.tapascodev.inspector.places.domain.usecases

import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.places.domain.PlaceRepository
import com.tapascodev.inspector.places.domain.model.Place
import javax.inject.Inject

class GetPlacesQueryUseCase@Inject constructor(
    val repository: PlaceRepository
) {
    suspend operator fun invoke(query:String, result: (Resource<List<Place>>) -> Unit) = repository.getPlacesQuery(query, result)
}