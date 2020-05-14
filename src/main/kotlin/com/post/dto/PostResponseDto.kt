package com.post.dto

import com.post.model.PostModel
import com.post.model.PostType

data class PostResponseDto(
    val id: Long,
    val ownerId: Long,
    val author: String,
    val createdDate: Long = (System.currentTimeMillis() / 1000L),
    var content: String? = null,
    var countLike: Long = 0,
    var isLike: Boolean = false,
    var countRepost: Long = 0,
    val type: PostType = PostType.POST,
    val adsUrl: String? = null,
    var countViews: Long = 0,
    val parentId: Long? = null,
    val imageId: Long? = null,
    val videoUrl: String? = null,
    val countComment: Long = 0,
    val isCanCommented: Boolean = true,
    val selectedLocation: String? = null
) {
    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
            id = model.id,
            ownerId = model.ownerId,
            author = model.author,
            createdDate = model.createdDate,
            content = model.content,
            countLike = model.countLike,
            isLike = model.isLike,
            countRepost = model.countRepost,
            type = model.type,
            adsUrl = model.adsUrl,
            countViews = model.countViews,
            parentId = model.parentId,
            imageId = model.imageId,
            videoUrl = model.videoUrl,
            countComment = model.countComment,
            isCanCommented = model.isCanCommented,
            selectedLocation = model.selectedLocation
        )
    }
}
