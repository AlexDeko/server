package com.post.db.data.reaction

import com.post.model.ReactionModel
import com.post.model.getReactionByString
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toReactions() = ReactionModel(
    id = this[Reactions.id],
    postId = this[Reactions.post_id],
    userId = this[Reactions.user_id],
    createdDate = this[Reactions.created_date],
    reactionType = getReactionByString(this[Reactions.reaction_type])
)