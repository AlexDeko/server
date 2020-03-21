package com.post.db.data.user

import org.jetbrains.exposed.sql.ResultRow
import com.post.model.UserModel

fun ResultRow.toUser() = UserModel(
    username = this[Users.username],
    id = this[Users.id],
    password = this[Users.password]
)