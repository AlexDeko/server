package com.post.dto

import com.post.model.PostModel
import com.post.model.PostTypeEnum
import kotlinx.serialization.Serializable

@Serializable
data class PostResponseDto(
    val id: Long,
    val ownerId: Long,
    val author: String,
    val createdDate: Long = (System.currentTimeMillis() / 1000L),
    val content: String = "",
    val countLike: Long = 0,
    val isLike: Boolean = false,
    val countRepost: Long = 0,
    val type: String = PostTypeEnum.POST.name,
    val adsUrl: String? = null,
    val countViews: Long = 0,
    val parentId: Long? = null,
    val imageId: String? = null,
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
