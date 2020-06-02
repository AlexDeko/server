package com.post.model

import kotlinx.serialization.Serializable

@Serializable
data class ReactionModel(
    val id: Long,
    val postId: Long,
    val userId: Long,
    val createdDate: Long,
    val reactionType: ReactionType
)

fun getReactionByString(reaction: String) = ReactionType.valueOf(reaction)