package com.post.db.data.post

import com.post.model.PostModel
import com.post.model.PostType
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toPost() = PostModel(
    id = this[Posts.id],
    ownerId = this[Posts.ownerId],
    author = this[Posts.author],
    createdDate = this[Posts.createdDate],
    content = this[Posts.content],
    isApprove = this[Posts.isApprove],
    countApprove = this[Posts.countApprove],
    isNotApprove = this[Posts.isNotApprove],
    countNotApprove = this[Posts.countNotApprove],
    countRepost = this[Posts.countRepost],
    type = PostType.valueOf(this[Posts.type]),
    urlLink = this[Posts.urlLink],
    countViews = this[Posts.countViews],
    parentId = this[Posts.parentId],
    imageId = this[Posts.imageId]
)