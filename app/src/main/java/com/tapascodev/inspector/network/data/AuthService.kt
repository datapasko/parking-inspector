package com.tapascodev.inspector.network.data

import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthService @Inject constructor(
    private val firebaseClient: FirebaseClient
) {

}