package com.post.dto

import com.post.model.PostType

data class PostRequestDto(
    val id: Long,
    val ownerId: Long,
    val author: String,
    val postType: PostType = PostType.POST,
    val text: String? = null,
    val date: String,
    val like: LikeDto,
    val comment: CommentDto,
    val reply: RepostDto? = null,
    val address: String? = null,
    val coordinates: Long? = null,
    val video: VideoDto? = null,
    val adsUrl: String? = null,
    var countViews: Long = 0,
    val parentId: Long? = null,
    val imageId: Long? = null
)