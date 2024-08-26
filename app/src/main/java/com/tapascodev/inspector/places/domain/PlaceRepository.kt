package com.tapascodev.inspector.places.domain

import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.places.domain.model.Place

interface PlaceRepository {
    suspend fun getPlaces(floor: Int, result: (Resource<List<Place>>) -> Unit)
    suspend fun getPlacesQuery(query:String, result: (Resource<List<Place>>) -> Unit)
}