package com.post.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserRegisterRequestDto(
    val username: String,
    val password: String
)