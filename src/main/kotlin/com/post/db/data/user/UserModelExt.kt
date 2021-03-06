package com.post.db.data.user

import com.post.model.UserModel
import com.post.model.getBadgeByString
import org.jetbrains.exposed.sql.ResultRow


fun ResultRow.toUser() = UserModel(
    id = this[Users.id],
    username = this[Users.username],
    password = this[Users.password],
    imageId = this[Users.image_id],
    badge = getBadgeByString(this[Users.badge].orEmpty()),
    notApprove = this[Users.not_approve],
    approve = this[Users.approve],
    onlyReads = this[Users.only_reads],
    firebaseId = this[Users.firebase_id]
)