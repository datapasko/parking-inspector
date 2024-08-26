package com.tapascodev.inspector.network.domain

import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

interface SafeApiCall {

    suspend fun <T>safeApiCall(
        apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Exception) {
                when (throwable) {
                    is HttpException, is FirebaseAuthException -> {
                        Resource.Failure(
                            false,
                            throwable.hashCode(),
                            throwable.message
                        )
                    }

                    else -> {
                        Resource.Failure(
                            true,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }
}