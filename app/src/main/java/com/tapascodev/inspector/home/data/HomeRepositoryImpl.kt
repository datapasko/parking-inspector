package com.tapascodev.inspector.home.data

import com.tapascodev.inspector.home.domain.HomeRepository
import com.tapascodev.inspector.network.data.FirebaseClient
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.network.domain.SafeApiCall
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    val firebaseClient: FirebaseClient
): HomeRepository, SafeApiCall {

    override suspend fun logout(): Resource<Unit> = safeApiCall {
        firebaseClient.auth.signOut()
    }
}