package com.post.db.data.user

import com.post.model.UserModel
import com.post.model.getBadgeByString
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.toUser() = UserModel(
    username = this[Users.username],
    id = this[Users.id],
    password = this[Users.password],
    tokenFirebase = this[Users.token_firebase],
    imageId = this[Users.image_id],
    badge = getBadgeByString(this[Users.badge]),
    notApprove = this[Users.not_approve],
    approve = this[Users.approve],
    onlyReads = this[Users.only_reads]
)