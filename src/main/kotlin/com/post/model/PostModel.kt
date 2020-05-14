package com.post.model

import com.google.gson.annotations.SerializedName

data class PostModel(
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

enum class PostType {
    @SerializedName("PostType.POST")
    POST,
    @SerializedName("PostType.REPOST")
    REPOST,
    @SerializedName("PostType.ADS")
    ADS,
    @SerializedName("PostType.VIDEO")
    VIDEO,
    @SerializedName("PostType.EVENT")
    EVENT
}
