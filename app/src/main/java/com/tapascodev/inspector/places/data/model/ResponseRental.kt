package com.tapascodev.inspector.places.data.model

import com.tapascodev.inspector.places.domain.model.Rental
import java.util.*

data class ResponseRental(
    var id : String = "",
    val vehicle: String = "",
    val start: Date = Date(),
    val end: Date = Date()
) {
    fun toDomain(): Rental {
        return Rental(
            id, vehicle, start, end
        )
    }
}
