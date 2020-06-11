package com.post.dto.token.firebase

import com.post.model.token.firebase.TokenFirebaseModel
import kotlinx.serialization.Serializable

@Serializable
class TokenFirebaseResponse(
    val id: Long = 0,
    val userId: Long,
    val token: String = ""
)

fun TokenFirebaseResponse.toModel() = TokenFirebaseModel(
    id = id,
    userId = userId,
    token = token
)