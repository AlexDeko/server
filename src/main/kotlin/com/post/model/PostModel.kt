package com.post.model

import kotlinx.serialization.Serializable

@Serializable
data class PostModel(
    val id: Long,
    val ownerId: Long,
    val author: String,
    val createdDate: Long = (System.currentTimeMillis() / 1000L),
    val content: String? = null,
    val countLike: Long = 0,
    val isLike: Boolean = false,
    val countRepost: Long = 0,
    val type: String = "пост",
    val adsUrl: String? = null,
    val countViews: Long = 0,
    val parentId: Long? = null,
    val imageId: Long? = null,
    val videoUrl: String? = null,
    val countComment: Long = 0,
    val isCanCommented: Boolean = true,
    val selectedLocation: String? = null
)