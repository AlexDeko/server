package com.post.dto

import com.post.model.PostModel
import com.post.model.PostType
import kotlinx.serialization.Serializable
import java.util.*

@Serializable
data class PostResponseDto(
    val id: Long,
    val ownerId: Long,
    val author: String,
    val createdDate: Long = Date().time,
    val content: String = "",
    val isApprove: Boolean = false,
    val countApprove: Long = 0,
    val isNotApprove: Boolean = false,
    val countNotApprove: Long = 0,
    val countRepost: Long = 0,
    val type: PostType = PostType.POST,
    val urlLink: String? = null,
    val countViews: Long = 0,
    val parentId: Long? = null,
    val imageId: String?= null
) {
    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
            id = model.id,
            ownerId = model.ownerId,
            author = model.author,
            createdDate = model.createdDate,
            content = model.content,
            isApprove = model.isApprove,
            countApprove = model.countApprove,
            isNotApprove = model.isNotApprove,
            countNotApprove = model.countNotApprove,
            countRepost = model.countRepost,
            type = model.type,
            countViews = model.countViews,
            parentId = model.parentId,
            imageId = model.imageId
        )
    }
}
