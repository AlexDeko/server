package com.post.model

import kotlinx.serialization.Serializable

@Serializable
enum class PostType {
    POST,
    REPOST
}