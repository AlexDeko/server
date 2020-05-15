package com.post.db.data.post

import com.post.model.PostModel
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toPost() = PostModel(
    id = this[Posts.id],
    ownerId = this[Posts.ownerId],
    author = this[Posts.author],
    createdDate = this[Posts.createdDate],
    content = this[Posts.content],
    countLike = this[Posts.countLike],
    isLike = this[Posts.isLike],
    countRepost = this[Posts.countRepost],
    type = this[Posts.type],
    adsUrl = this[Posts.adsUrl],
    countViews = this[Posts.countViews],
    parentId = this[Posts.parentId],
    imageId = this[Posts.imageId],
    videoUrl = this[Posts.videoUrl],
    countComment = this[Posts.countComment],
    isCanCommented = this[Posts.isCanCommented],
    selectedLocation = this[Posts.selectedLocation]
)