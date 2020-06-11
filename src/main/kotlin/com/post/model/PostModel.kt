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
    var isApprove: Boolean = false,
    val countApprove: Long = 0,
    var isNotApprove: Boolean = false,
    val countNotApprove: Long = 0,
    val countRepost: Long = 0,
    val type: PostType = PostType.POST,
    val urlLink: String? = null,
    val countViews: Long = 0,
    val parentId: Long? = null,
    val imageId: String?= null
)


