package com.post.model.token.firebase

import kotlinx.serialization.Serializable

@Serializable
data class TokenFirebaseModel(
    val id: Long,
    val userId: Long,
    val token: String
)