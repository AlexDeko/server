package com.post.model

import kotlinx.serialization.Serializable

@Serializable
enum class ReactionType {
    APPROVE,
    NOT_APPROVE
}