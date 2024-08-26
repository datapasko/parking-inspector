package com.tapascodev.inspector.auth.domain

import javax.inject.Inject

class CurrentUserUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke() = repository.currentUser()
}