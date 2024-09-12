package com.tapascodev.inspector.places.domain.model

data class Place (
    var id: String,
    val number: Int,
    val floor: Int,
    val size: String,
    var currentRental: Rental?
)