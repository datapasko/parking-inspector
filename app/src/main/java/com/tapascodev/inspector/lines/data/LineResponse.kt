package com.tapascodev.inspector.lines.data

import com.google.gson.annotations.SerializedName
import com.tapascodev.inspector.lines.domain.LineModel

data class LineResponse (
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("zone") val zone: String?,
) {
    fun toDomain(): LineModel {
        return LineModel(
            id,
            name,
            type,
            zone
        )
    }
}