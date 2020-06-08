package com.post.repository

import com.post.db.data.reaction.Reaction
import com.post.db.data.reaction.toReaction
import com.post.db.dbQuery
import com.post.model.ReactionModel
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll

class ReactionRepositoryImpl : ReactionRepository {
    override suspend fun geAllByPostId(id: Long): List<ReactionModel> =
        dbQuery {
            Reaction.selectAll().andWhere {
                Reaction.post_id eq id
            }.toList().map {
                it.toReaction()
            }
        }


    override suspend fun save(item: ReactionModel) {
       dbQuery {
           Reaction.insert { insertStatement ->
               insertStatement[post_id] = item.postId
               insertStatement[user_id] = item.userId
               insertStatement[created_date] = item.createdDate
               insertStatement[reaction_type] = item.reactionType.name
           }
       }
    }
}