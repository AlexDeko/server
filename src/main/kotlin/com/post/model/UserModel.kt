package com.post.model

import io.ktor.auth.Principal
import com.post.dto.user.UserResponseDto
import kotlinx.serialization.Serializable

@Serializable
data class UserModel(
    val id: Long = 0,
    val username: String,
    val password: String,
    val tokenFirebase: String? = null,
    val imageId: Long? = null,
    val badge: UserBadge? = null,
    val notApprove: Long = 0,
    val approve: Long = 0,
    val onlyReads: Boolean = false
): Principal

fun UserModel.toDto() = UserResponseDto(
    id = id,
    username = username,
    imageId = imageId,
    badge = badge,
    notApprove = notApprove,
    approve = approve,
    onlyReads = onlyReads
)

fun getBadgeByString(badge: String): UserBadge? {
    for (i in 0..UserBadge.values().size) {
        val targetBadge = UserBadge.values()[i]
        if (targetBadge.name == badge) return targetBadge
    }
    return null
}