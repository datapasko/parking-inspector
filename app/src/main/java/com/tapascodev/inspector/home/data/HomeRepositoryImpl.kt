package com.tapascodev.inspector.home.data

import com.tapascodev.inspector.home.domain.HomeRepository
import com.tapascodev.inspector.network.data.FirebaseClient
import com.tapascodev.inspector.network.domain.SafeApiCall
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val firebaseClient: FirebaseClient
): HomeRepository, SafeApiCall {

    /*override suspend fun getCurrentUser(): Resource<User> = safeApiCall{
        val id = firebaseClient.auth.currentUser!!.uid

        firebaseClient.db.collection("users").document(id).get(Source.CACHE).await()

    }*/
}