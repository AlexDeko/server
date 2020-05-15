package com.post.db.data.post

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Posts : Table() {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val ownerId: Column<Long> = long("ownerId")
    val author: Column<String> = varchar("author", 100)
    val createdDate: Column<Long> = long("createdDate")
    val content: Column<String?> = varchar("contentText", 100).nullable()
    val countLike: Column<Long> = long("countLike")
    val isLike: Column<Boolean> = bool("isLike")
    val countRepost: Column<Long> = long("countRepost")
    val type: Column<String> = varchar("postType", 100)
    val adsUrl: Column<String?> = varchar("adsUrl", 100).nullable()
    val countViews: Column<Long> = long("countViews")
    val parentId: Column<Long?> = long("parentId").nullable()
    val imageId: Column<Long?> = long("imageId").nullable()
    val videoUrl: Column<String?> = varchar("videoUrl", 100).nullable()
    val countComment: Column<Long> = long("countComment")
    val isCanCommented: Column<Boolean> = bool("isCanCommented")
    val selectedLocation: Column<String?> = varchar("selectedLocation", 100).nullable()
}