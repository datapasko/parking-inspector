package com.tapascodev.inspector.base.domain

import com.tapascodev.inspector.network.domain.SafeApiCall

interface BaseRepository {
    suspend fun logout(): Boolean
}