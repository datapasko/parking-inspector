package com.tapascodev.inspector.auth.domain

import com.tapascodev.inspector.auth.domain.model.UserSignIn
import com.tapascodev.inspector.network.domain.Resource
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(userSignIn: UserSignIn): Resource<Boolean> = repository.createUser(userSignIn)
}