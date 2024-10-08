package com.jozefv.newsdata.core.data

import com.jozefv.newsdata.core.domain.AuthUser
import kotlinx.serialization.Serializable

fun AuthUser.toAuthSerialized(): AuthUserSerialized {
    return AuthUserSerialized(email = email, password = password)
}

fun AuthUserSerialized.toAuthUser(): AuthUser {
    return AuthUser(
        email = email,
        password = password
    )
}


@Serializable
data class AuthUserSerialized(
    val email: String,
    val password: String
)