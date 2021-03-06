package com.post.model

import com.post.dto.reaction.ReactionResponseDto
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class ReactionModel(
    val id: Long = 0,
    val postId: Long,
    val userId: Long,
    val createdDate: Long = Date().time,
    val reactionType: ReactionType
)

fun getReactionByString(reaction: String) = ReactionType.valueOf(reaction)

fun ReactionModel.toDto() = ReactionResponseDto(
    id = id,
    postId = postId,
    userId = userId,
    createdDate = createdDate,
    reactionType = reactionType
)