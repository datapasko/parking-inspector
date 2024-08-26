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
import com.tapascodev.inspector.places.domain.PlaceRepository
import com.tapascodev.inspector.places.domain.model.Place
import kotlinx.coroutines.tasks.await
import okhttp3.internal.format
import javax.inject.Inject

class PlaceRepositoryImpl @Inject constructor(
    val firebaseClient: FirebaseClient
): PlaceRepository, SafeApiCall {

    override suspend fun getPlaces(floor: Int, result: (Resource<List<Place>>) -> Unit) {

        firebaseClient.db.collection(FireStoreCollection.PLACES)
            .whereEqualTo(FireStoreDocumentField.FLOOR, floor)
            .orderBy(FireStoreDocumentField.NUMBER, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val data = mutableListOf<Place>()
                for(doc in documents) {
                    val place = doc.toObject(ResponsePlace::class.java)
                    data.add(place.toDomain())
                }

                result.invoke(
                    Resource.Success(data)
                )
            }.addOnFailureListener {
                result.invoke(
                    Resource.Failure(
                        false,
                        0,
                        it.localizedMessage,
                    )
                )
            }
    }

    override suspend fun getPlacesQuery(query: String, result: (Resource<List<Place>>) -> Unit) {
        firebaseClient.db.collection(FireStoreCollection.PLACES)
            .whereEqualTo(FireStoreDocumentField.NUMBER, query.toInt())
            .orderBy(FireStoreDocumentField.NUMBER, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val data = mutableListOf<Place>()
                for(doc in documents) {
                    val place = doc.toObject(ResponsePlace::class.java)
                    data.add(place.toDomain())
                    Log.d("messi", "messi")
                }

                result.invoke(
                    Resource.Success(data)
                )
            }.addOnFailureListener {
                result.invoke(
                    Resource.Failure(
                        false,
                        0,
                        it.localizedMessage,
                    )
                )
            }
    }

}