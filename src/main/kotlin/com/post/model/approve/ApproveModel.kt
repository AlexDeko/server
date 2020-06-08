package com.post.model.approve

import kotlinx.serialization.Serializable

@Serializable
data class ApproveModel(
    val id: Long,
    val userId: Long,
    val postId: Long,
    val isApprove: Boolean,
    val isNotApprove: Boolean
)