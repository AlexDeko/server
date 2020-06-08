package com.post.db.data.token.fairbase

import com.post.model.token.firebase.TokenFirebaseModel
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toTokenFirebase() = TokenFirebaseModel(
    id = this[TokensFirebase.id],
    userId = this[TokensFirebase.user_id],
    token = this[TokensFirebase.token]
)