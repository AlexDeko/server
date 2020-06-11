package com.post.model

import com.post.dto.user.UserResponseDto
import io.ktor.auth.Principal
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Long = 0,
    val username: String,
    val password: String,
    val imageId: Long? = null,
    val badge: UserBadge? = null,
    val notApprove: Long = 0,
    val approve: Long = 0,
    val onlyReads: Boolean = false,
    val firebaseId: String? = null
) : Principal

fun UserModel.toDto() = UserResponseDto(
    id = id,
    username = username,
    imageId = imageId,
    badge = badge,
    notApprove = notApprove,
    approve = approve,
    onlyReads = onlyReads,
    firebaseId = firebaseId
)

fun getBadgeByString(badge: String): UserBadge? = UserBadge.values().find {
    it.name == badge
}