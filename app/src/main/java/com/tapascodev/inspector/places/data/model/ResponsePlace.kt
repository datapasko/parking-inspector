package com.tapascodev.inspector.places.data.model

import android.os.Parcelable
import com.tapascodev.inspector.places.domain.model.Place
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponsePlace (
    val id: String = "",
    val number: Int = 0,
    val floor: Int = 0,
    val size: String = ""
): Parcelable {

    fun toDomain(): Place {
        return Place( id, number, floor, size)
    }
}