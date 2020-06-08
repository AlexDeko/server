package com.post.db.data.approve

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Approves : Table() {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val userId: Column<Long> = long("user_id")
    val postId: Column<Long> = long("post_id")
    val isApprove: Column<Boolean> = bool("is_approve")
    val isNotApprove: Column<Boolean> = bool("is_not_approve")
}