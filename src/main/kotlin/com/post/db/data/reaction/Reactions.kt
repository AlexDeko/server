package com.post.db.data.reaction

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Reactions : Table() {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val post_id: Column<Long> = long("post_id")
    val user_id: Column<Long> = long("user_id")
    val created_date: Column<Long> = long("created_date")
    val reaction_type: Column<String> = varchar("reaction_type", 100)
}