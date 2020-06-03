package com.post.model

import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PostModel(
    val id: Long,
    val ownerId: Long,
    val author: String,
    val createdDate: Long = Date().time,
    val content: String = "",
    val countLike: Long = 0,
    val isLike: Boolean = false,
    val countRepost: Long = 0,
    val type: String = PostType.POST.name,
    val adsUrl: String? = null,
    val countViews: Long = 0,
    val parentId: Long? = null,
    val imageId: String? = null,
    val videoUrl: String? = null,
    val countComment: Long = 0,
    val isCanCommented: Boolean = true,
    val selectedLocation: String? = null
)

