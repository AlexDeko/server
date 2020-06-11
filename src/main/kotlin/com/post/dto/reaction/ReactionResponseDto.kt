package com.post.dto.reaction

import com.post.model.ReactionModel
import com.post.model.ReactionType
import java.util.*

data class ReactionResponseDto(
    val id: Long = 0,
    val postId: Long,
    val userId: Long,
    val createdDate: Long = Date().time,
    val reactionType: ReactionType
)

fun ReactionResponseDto.toModel() = ReactionModel(
    id = id,
    postId = postId,
    userId = userId,
    createdDate = createdDate,
    reactionType = reactionType
)