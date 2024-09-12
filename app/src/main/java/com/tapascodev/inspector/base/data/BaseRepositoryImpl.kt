package com.tapascodev.inspector.base.data

import com.tapascodev.inspector.base.domain.BaseRepository
import com.tapascodev.inspector.network.data.FirebaseClient
import javax.inject.Inject

class BaseRepositoryImpl @Inject constructor(
    val firebaseClient: FirebaseClient
): BaseRepository {

    override suspend fun logout(): Boolean {
        return true

        //return firebaseClient.auth.signOut()
    }
}