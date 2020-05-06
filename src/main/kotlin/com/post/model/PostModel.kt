package com.post.model

import com.google.gson.annotations.SerializedName
import com.post.dto.CommentDto
import com.post.dto.LikeDto
import com.post.dto.RepostDto
import com.post.dto.VideoDto

data class PostModel(
    val id: Long,
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
    val countViews: Long = 0
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
