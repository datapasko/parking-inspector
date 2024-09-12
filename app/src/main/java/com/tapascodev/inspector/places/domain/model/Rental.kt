package com.tapascodev.inspector.places.domain.model

import java.util.Date

data class Rental(
    val id : String,
    val vehicle: String,
    val start: Date,
    val end: Date?
)
