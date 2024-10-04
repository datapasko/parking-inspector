package com.tapascodev.inspector.vehicles.data

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.tapascodev.inspector.auth.data.AuthRepositoryImpl.Companion.USER_COLLECTION
import com.tapascodev.inspector.base.ui.FireStoreCollection
import com.tapascodev.inspector.base.ui.FireStoreDocumentField
import com.tapascodev.inspector.network.data.FirebaseClient
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.network.domain.SafeApiCall
import com.tapascodev.inspector.vehicles.domain.VehicleRepository
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    val firebaseClient: FirebaseClient
): VehicleRepository, SafeApiCall {

    override suspend fun getVehicles(): Resource<List<Vehicle>> = safeApiCall {
        println("load vehicles")
        firebaseClient.db.collection(FireStoreCollection.VEHICLES)
            .orderBy(FireStoreDocumentField.PLATE, Query.Direction.ASCENDING)
            .get()
            .await()
            .toList()
            .mapNotNull { doc ->
                val vehicle = doc.toObject(VehicleResponse::class.java)
                vehicle.id = doc.id
                vehicle.toDomain()
            }
    }

    override suspend fun getByPlate(plate: String): Resource<List<Vehicle>> = safeApiCall  {
        firebaseClient.db.collection(FireStoreCollection.VEHICLES)
            .whereGreaterThanOrEqualTo(FireStoreDocumentField.PLATE, plate)
            .whereLessThan(FireStoreDocumentField.PLATE, plate + "\uf8ff")
            .orderBy(FireStoreDocumentField.PLATE, Query.Direction.ASCENDING)
            .get()
            .await()
            .toList()
            .mapNotNull { doc ->
                val vehicle = doc.toObject(VehicleResponse::class.java)
                vehicle.id = doc.id
                vehicle.toDomain()
            }
    }

    override suspend fun getVehicle(id: String): Resource<Vehicle> {
        val response = firebaseClient.db.collection(FireStoreCollection.VEHICLES)
            .document(id)
            .get()
            .addOnFailureListener {
                Resource.Failure(
                    false,
                    0,
                    it.localizedMessage,
                )
            }
            .await()


        val vehicle = response.toObject(VehicleResponse::class.java)
        vehicle?.id = response.id

        return if (vehicle != null) {
            Resource.Success(vehicle.toDomain())
        } else{
            Resource.Failure(
                false,
                0,
                "not found vehicle",
            )
        }
    }

    override suspend fun addVehicle(vehicle: Vehicle): Resource<Boolean> = safeApiCall {
        val data = hashMapOf(
            "plate" to vehicle.plate,
            "brand" to vehicle.brand,
            "model" to vehicle.model,
            "color" to vehicle.color,
            "name" to vehicle.name,
            "email" to vehicle.email,
            "phone" to vehicle.phone,
            "address" to vehicle.address,
            "city" to vehicle.city,
            "code_postal" to vehicle.codePostal,
            "active" to true,
        )

        firebaseClient.db.collection(FireStoreCollection.VEHICLES).add(data).await().id.isNotEmpty()
    }

    override suspend fun updateVehicle(vehicle: Vehicle): Resource<Boolean> = safeApiCall {
        println("update vehicle")
        val data = hashMapOf<String, Any>(
            "plate" to vehicle.plate,
            "brand" to vehicle.brand,
            "model" to vehicle.model,
            "color" to vehicle.color,
            "name" to vehicle.name,
            "email" to vehicle.email,
            "phone" to vehicle.phone,
            "address" to vehicle.address,
            "city" to vehicle.city,
            "code_postal" to vehicle.codePostal,
            "active" to true,
        )

        firebaseClient.db.collection(FireStoreCollection.VEHICLES).document(vehicle.id).update(data).await()
        true
    }
}