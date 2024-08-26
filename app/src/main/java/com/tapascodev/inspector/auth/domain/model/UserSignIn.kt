package com.tapascodev.inspector.auth.domain.model

data class UserSignIn (
    val id: String?,
    val name: String,
    val nickname: String,
    val email: String,
    val password: String
){
    fun isNotEmpty() =
        name.isNotEmpty() && nickname.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()
}