package com.post.dto

import com.post.model.PostType
import kotlinx.serialization.Serializable

@Serializable
data class PostRequestDto(
    val id: Long,
    val ownerId: Long,
    val author: String,
    val createdDate: Long = (System.currentTimeMillis() / 1000L),
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