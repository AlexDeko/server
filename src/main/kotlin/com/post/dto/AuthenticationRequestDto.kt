package com.post.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticationRequestDto(val username: String, val password: String)
