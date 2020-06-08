package com.post.db.data.approve

import com.post.model.approve.ApproveModel
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toApprove() = ApproveModel(
    id = this[Approves.id],
    userId = this[Approves.userId],
    postId = this[Approves.postId],
    isApprove = this[Approves.isApprove],
    isNotApprove = this[Approves.isNotApprove]
)