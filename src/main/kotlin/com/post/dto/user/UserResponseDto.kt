package com.post.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val id: Long,
    val username: String
)