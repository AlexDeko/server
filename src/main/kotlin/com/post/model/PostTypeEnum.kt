package com.post.model

import kotlinx.serialization.Serializable

@Serializable
enum class PostTypeEnum {
    POST,
    REPOST,
    ADS,
    VIDEO,
    EVENT
}