package com.tapascodev.inspector.vehicles.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.google.j2objc.annotations.Property
import com.tapascodev.inspector.vehicles.domain.model.Vehicle
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleResponse (
    var id : String = "",
    val active : Boolean = false,
    val address: String = "",
    val brand: String = "",
    val city: String = "",
    @Property("code_postal") val codePostal: String = "",
    val color: String = "",
    val email: String = "",
    val model: String = "",
    val name: String = "",
    val phone: String = "",
    val plate: String = ""
): Parcelable {

    fun toDomain(): Vehicle {
        return Vehicle(id, active, address, brand, city, codePostal, color, email, model, name, phone, plate )
    }

}