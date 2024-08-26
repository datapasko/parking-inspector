package com.tapascodev.inspector.auth.domain

import com.tapascodev.inspector.auth.domain.model.UserSignIn
import com.tapascodev.inspector.network.domain.Resource

interface AuthRepository {
    suspend fun login(email: String, password: String): Resource<Boolean>
    suspend fun register(userSignIn: UserSignIn): Resource<Boolean>
    suspend fun createUser(userSignIn: UserSignIn): Resource<Boolean>
    suspend fun currentUser(): Resource<Boolean>
}