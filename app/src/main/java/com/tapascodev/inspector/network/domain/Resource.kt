package com.tapascodev.inspector.network.domain

sealed class Resource<out T> {
    data object Loading: Resource<Nothing>()
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: String?
    ) : Resource<Nothing>()
}