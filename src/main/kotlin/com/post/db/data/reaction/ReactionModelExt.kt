package com.post.db.data.reaction

import com.post.model.ReactionModel
import com.post.model.getReactionByString
import org.jetbrains.exposed.sql.ResultRow

fun ResultRow.toReaction() = ReactionModel(
    id = this[Reaction.id],
    postId = this[Reaction.post_id],
    userId = this[Reaction.user_id],
    createdDate = this[Reaction.created_date],
    reactionType = getReactionByString(this[Reaction.reaction_type])
)