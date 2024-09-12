package com.tapascodev.inspector.vehicles.data

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.toObject
import com.tapascodev.inspector.base.ui.FireStoreCollection
import com.tapascodev.inspector.base.ui.FireStoreDocumentField
import com.tapascodev.inspector.network.data.FirebaseClient
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.network.domain.SafeApiCall
import com.tapascodev.inspector.places.domain.model.Place
import com.tapascodev.inspector.vehicles.domain.VehicleRepository
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    val firebaseClient: FirebaseClient
): VehicleRepository {

    private val vehicles = mutableListOf<Vehicle>()

    override suspend fun getVehicles(result: (Resource<List<Vehicle>>) -> Unit) {

        firebaseClient.db.collection(FireStoreCollection.VEHICLES)
            .orderBy(FireStoreDocumentField.PLATE, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for(doc in documents){
                    val vehicle = doc.toObject(VehicleResponse::class.java)
                    vehicle.id = doc.id
                    vehicles.add(vehicle.toDomain())
                }
                result.invoke(
                    Resource.Success(vehicles)
                )
            }
            .addOnFailureListener {
                result.invoke(
                    Resource.Failure(
                        false,
                        0,
                        it.localizedMessage,
                    )
                )
            }
    }

    override suspend fun getByPlate(plate: String, result: (Resource<List<Vehicle>>) -> Unit) {

        firebaseClient.db.collection(FireStoreCollection.VEHICLES)
            .orderBy(FireStoreDocumentField.PLATE, Query.Direction.ASCENDING)
            .whereGreaterThanOrEqualTo(FireStoreDocumentField.PLATE, Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                for(doc in documents){
                    val vehicle = doc.toObject(VehicleResponse::class.java)
                    vehicle.id = doc.id
                    vehicles.add(vehicle.toDomain())
                }
                result.invoke(
                    Resource.Success(vehicles)
                )
            }
            .addOnFailureListener {
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