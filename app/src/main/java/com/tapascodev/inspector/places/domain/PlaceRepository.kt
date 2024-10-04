package com.tapascodev.inspector.places.domain

import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.places.domain.model.Place

interface PlaceRepository {
    suspend fun getPlaces(): Resource<List<Place>>
    suspend fun getPlacesQuery(query:String): Resource<List<Place>>
}