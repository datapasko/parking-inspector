package com.tapascodev.inspector.auth.data

import com.tapascodev.inspector.auth.domain.AuthRepository
import com.tapascodev.inspector.auth.domain.model.UserSignIn
import com.tapascodev.inspector.network.data.FirebaseClient
import com.tapascodev.inspector.network.domain.Resource
import com.tapascodev.inspector.network.domain.SafeApiCall
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseClient: FirebaseClient
): AuthRepository, SafeApiCall {

    companion object {
        const val USER_COLLECTION = "users"
    }

    override suspend fun login(email: String, password: String): Resource<Boolean> = safeApiCall {
        firebaseClient.auth.signInWithEmailAndPassword(email,password).await() != null
    }

    override suspend fun register(userSignIn: UserSignIn): Resource<Boolean> = safeApiCall {
        firebaseClient.auth.createUserWithEmailAndPassword(userSignIn.email, userSignIn.password).await() != null
    }

    override suspend fun createUser(userSignIn: UserSignIn): Resource<Boolean> = safeApiCall{
        val user = hashMapOf(
            "email" to userSignIn.email,
            "name" to userSignIn.name,
            "nickname" to userSignIn.nickname,
        )
        firebaseClient.db.collection(USER_COLLECTION).add(user).await() != null
    }

    override suspend fun currentUser(): Resource<Boolean> = safeApiCall {
        firebaseClient.auth.currentUser != null
    }


}
