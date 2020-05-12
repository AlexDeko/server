package com.post.dto

import com.post.model.PostModel
import com.post.model.PostType

data class PostResponseDto(
    val id: Long,
    val author: String,
    val postType: PostType = PostType.POST,
    val text: String? = null,
    val date: String,
    val like: LikeDto,
    val comment: CommentDto,
    val reply: RepostDto? = null,
    val address: String? = null,
    private val coordinates: Long? = null,
    val video: VideoDto? = null,
    val adsUrl: String? = null,
    val countViews: Long = 0,
    val parentId: Long? = null
) {
    companion object {
        fun fromModel(model: PostModel) = PostResponseDto(
            id = model.id,
            author = model.author,
            postType = model.postType,
            text = model.text,
            date = model.date,
            like = model.like,
            comment = model.comment,
            reply = model.reply,
            address = model.address,
            coordinates = model.coordinates,
            video = model.video,
            adsUrl = model.adsUrl,
            countViews = model.countViews,
            parentId = model.parentId
        )
    }
}
