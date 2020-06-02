package com.post.repository

import com.post.db.data.reaction.Reactions
import com.post.db.data.reaction.toReaction
import com.post.db.dbQuery
import com.post.model.ReactionModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll

class ReactionRepositoryImpl : ReactionRepository {
    override suspend fun geAllByPostId(id: Long): List<ReactionModel> =
        dbQuery {
            Reactions.selectAll().andWhere {
                Reactions.post_id eq id
            }.toList().map {
                it.toReaction()
            }
        }


    override suspend fun save(item: ReactionModel) {
       dbQuery {
           Reactions.insert { insertStatement ->
               insertStatement[post_id] = item.postId
               insertStatement[user_id] = item.userId
               insertStatement[created_date] = item.createdDate
               insertStatement[reaction_type] = item.reactionType.name
           }
       }
    }
}