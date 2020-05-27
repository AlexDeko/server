package com.post.db.data.post

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Posts : Table() {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val ownerId: Column<Long> = long("owner_id")
    val author: Column<String> = varchar("author", 100)
    val createdDate: Column<Long> = long("created_date")
    val content: Column<String> = varchar("content_text", 100)
    val countLike: Column<Long> = long("count_like")
    val isLike: Column<Boolean> = bool("is_like")
    val countRepost: Column<Long> = long("count_repost")
    val type: Column<String> = varchar("post_type", 100)
    val adsUrl: Column<String?> = varchar("ads_url", 100).nullable()
    val countViews: Column<Long> = long("count_views")
    val parentId: Column<Long?> = long("parent_id").nullable()
    val imageId: Column<String?> = varchar("image_id", 100).nullable()
    val videoUrl: Column<String?> = varchar("video_url", 100).nullable()
    val countComment: Column<Long> = long("count_comment")
    val isCanCommented: Column<Boolean> = bool("is_can_commented")
    val selectedLocation: Column<String?> = varchar("selected_location", 100).nullable()
}