package com.post.db.data.post

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Posts : Table() {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val ownerId: Column<Long> = long("owner_id")
    val author: Column<String> = varchar("author", 100)
    val createdDate: Column<Long> = long("created_date")
    val content: Column<String> = varchar("content_text", 100)
    val isApprove: Column<Boolean> = bool("is_approve")
    val countApprove: Column<Long> = long("count_approve")
    val isNotApprove: Column<Boolean> = bool("is_not_approve")
    val countNotApprove: Column<Long> = long("count_not_approve")
    val countRepost: Column<Long> = long("count_repost")
    val type: Column<String> = varchar("post_type", 100)
    val urlLink: Column<String?> = varchar("url_link", 100).nullable()
    val countViews: Column<Long> = long("count_views")
    val parentId: Column<Long?> = long("parent_id").nullable()
    val imageId: Column<String?> = varchar("image_id", 100).nullable()
}