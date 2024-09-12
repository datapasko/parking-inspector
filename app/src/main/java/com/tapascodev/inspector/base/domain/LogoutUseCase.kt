package com.tapascodev.inspector.base.domain

import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    val repository: BaseRepository
) {
    suspend operator fun invoke(result: (Boolean) -> Unit) = repository.logout()
}