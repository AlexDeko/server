package com.post.db.data.token.fairbase

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object TokensFirebase : Table() {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val user_id: Column<Long> = long("user_id")
    val token: Column<String> = varchar("token",1000)
}