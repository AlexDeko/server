package com.post.db.data.user

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object Users : Table() {
    val id: Column<Long> = long("id").autoIncrement().primaryKey()
    val username: Column<String> = varchar("username", 100)
    val password: Column<String> = varchar("password", 100)
    val token_firebase: Column<String> = varchar("token_firebase", Int.MAX_VALUE)
    val image_id: Column<Long> = long("image_id")
    val badge: Column<String> = varchar("badge", 100)
    val not_approve: Column<Long> = long("not_approve")
    val approve: Column<Long> = long("approve")
    val only_reads: Column<Boolean> = bool("only_reads")
}