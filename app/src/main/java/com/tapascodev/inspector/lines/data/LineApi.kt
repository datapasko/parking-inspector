package com.tapascodev.inspector.lines.data

import com.tapascodev.inspector.base.data.BaseApi
import retrofit2.http.GET

interface LineApi: BaseApi {

    @GET("lines")
    suspend fun lines(): List<LineResponse>
}