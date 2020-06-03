package com.post.dto.user

import com.post.model.UserBadge
import kotlinx.serialization.Serializable

@Serializable
data class UserResponseDto(
    val id: Long,
    val username: String,
    val imageId: Long? = null,
    val badge: UserBadge? = null,
    val notApprove: Long = 0,
    val approve: Long = 0,
    val onlyReads: Boolean = false,
    val firebaseId: String? = null
)