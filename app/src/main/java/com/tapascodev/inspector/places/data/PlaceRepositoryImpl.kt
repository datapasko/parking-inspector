package com.tapascodev.inspector.places.data

import android.util.Log
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.tapascodev.inspector.base.ui.FireStoreCollection
import com.tapascodev.inspector.base.ui.FireStoreDocumentField
import com.tapascodev.inspector.network.data.FirebaseClient
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.network.domain.SafeApiCall
import com.tapascodev.inspector.places.data.model.ResponsePlace
import com.tapascodev.inspector.places.data.model.ResponseRental
import com.tapascodev.inspector.places.domain.PlaceRepository
import com.tapascodev.inspector.places.domain.model.Place
import com.tapascodev.inspector.places.domain.model.Rental
import kotlinx.coroutines.tasks.await
import okhttp3.internal.format
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    val firebaseClient: FirebaseClient
): PlaceRepository, SafeApiCall {

    override suspend fun getPlaces(floor: Int, result: (Resource<List<Place>>) -> Unit) {

        val data = mutableListOf<Place>()

        val response = firebaseClient.db.collection(FireStoreCollection.PLACES)
            .whereEqualTo(FireStoreDocumentField.FLOOR, floor)
            .orderBy(FireStoreDocumentField.NUMBER, Query.Direction.ASCENDING)
            .get()
            .addOnFailureListener {
                result.invoke(
                    Resource.Failure(
                        false,
                        0,
                        it.localizedMessage,
                    )
                )
            }
            .await()

        response.toList().forEach { doc ->
            doc.let {
                val place = it.toObject(ResponsePlace::class.java).toDomain()
                place.id = doc.id
                place.currentRental = getCurrentRental(place.id)?.toDomain()
                data.add(place)
            }
        }

        result.invoke(
            Resource.Success(data)
        )
    }

    private suspend fun getCurrentRental(id: String): ResponseRental? {

        val response =  firebaseClient.db.collection(FireStoreCollection.PLACES)
            .document(id)
            .collection(FireStoreCollection.RENTALS)
            .whereEqualTo(FireStoreDocumentField.END, null)
            .get()
            .await()
            .firstOrNull()

        val rental = response?.toObject(ResponseRental::class.java)
        rental?.id = response?.id.toString()

        return rental
    }

    override suspend fun getPlacesQuery(query: String, result: (Resource<List<Place>>) -> Unit) {
        val data = mutableListOf<Place>()
        val response = firebaseClient.db.collection(FireStoreCollection.PLACES)
            .whereEqualTo(FireStoreDocumentField.NUMBER, query.toInt())
            .orderBy(FireStoreDocumentField.NUMBER, Query.Direction.ASCENDING)
            .get()
            .addOnFailureListener {
                result.invoke(
                    Resource.Failure(
                        false,
                        0,
                        it.localizedMessage,
                    )
                )
            }
            .await()

        response.toList().forEach { doc ->
            doc.let {
                val place = it.toObject(ResponsePlace::class.java).toDomain()
                place.id = doc.id
                place.currentRental = getCurrentRental(place.id)?.toDomain()
                data.add(place)
            }
        }

        result.invoke(
            Resource.Success(data)
        )
    }

}