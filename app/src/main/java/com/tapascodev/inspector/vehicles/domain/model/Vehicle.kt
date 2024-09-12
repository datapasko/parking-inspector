package com.tapascodev.inspector.vehicles.domain.model

data class Vehicle(
    val id: String = "",
    val active : Boolean = false,
    val address: String = "",
    val brand: String = "",
    val city: String = "",
    val codePostal: String = "",
    val color: String = "",
    val email: String = "",
    val model: String = "",
    val name: String = "",
    val phone: String = "",
    val plate: String = ""
)
