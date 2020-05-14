package com.post.dto

import com.post.model.PostModel
import com.post.model.PostType

data class PostRequestDto(
    val id: Long,
    val ownerId: Long,
    val author: String,
    val createdDate: Long = (System.currentTimeMillis() / 1000L),
    var content: String? = null,
    var countLike: Long = 0,
    var isLike: Boolean = false,
    var countRepost: Int = 0,
    val type: PostType = PostType.POST,
    val adsUrl: String? = null,
    var countViews: Long = 0,
    val parentId: Long? = null,
    val source: PostModel? = null,
    val imageId: Long? = null,
    val videoUrl: String? = null,
    val countComment: Long = 0,
    val isCanCommented: Boolean = true,
    val location: String? = null
)