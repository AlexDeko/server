package com.post.model

import io.ktor.auth.Principal
import com.post.dto.user.UserResponseDto
import kotlin.random.Random

data class UserModel(
    val id: Long = 0,
    val username: String,
    val password: String
): Principal

fun UserModel.toDto() = UserResponseDto(
    id,
    username
)