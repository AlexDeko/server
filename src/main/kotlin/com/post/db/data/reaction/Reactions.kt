package com.post.db.data.reaction

import com.post.db.data.post.Posts
import com.post.db.data.post.Posts.autoIncrement
import com.post.db.data.post.Posts.primaryKey
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Reactions : Table() {
    val id: Column<Long> = Reactions.long("id").autoIncrement().primaryKey()
    val post_id: Column<Long> = Reactions.long("post_id")
    val user_id: Column<Long> = Reactions.long("user_id")
    val created_date: Column<Long> = Reactions.long("created_date")
    val reaction_type: Column<String> = Reactions.varchar("reaction_type", 100)
}