package com.tapascodev.inspector.home.domain

import com.tapascodev.inspector.network.domain.Resource

interface HomeRepository {
    //suspend fun getCurrentUser(): Resource<User>
    suspend fun logout(): Resource<Unit>
}