package com.post.repository

import com.post.db.data.reaction.Reactions
import com.post.db.data.reaction.toReactions
import com.post.db.dbQuery
import com.post.model.ReactionModel
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ReactionRepositoryImpl : ReactionRepository {

    override suspend fun geAllByPostId(idPost: Long): List<ReactionModel> =
        dbQuery {
            Reactions.selectAll().andWhere {
                Reactions.post_id eq idPost
            }.toList().map {
                it.toReactions()
            }
        }

    override suspend fun getByIdPostAndIdUser(idPost: Long, idUser: Long): ReactionModel? =
        dbQuery {
            Reactions.select(Reactions.post_id eq idPost)
                .andWhere { Reactions.user_id eq idUser }
                .singleOrNull()?.toReactions()
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

    override suspend fun update(item: ReactionModel) {
        dbQuery {
            Reactions.update(where = {
                (Reactions.user_id eq item.userId).and(Reactions.post_id eq item.postId)
            }, body = { updateStatement ->
                updateStatement[post_id] = item.postId
                updateStatement[user_id] = item.userId
                updateStatement[created_date] = item.createdDate
                updateStatement[reaction_type] = item.reactionType.name
            })
        }
    }


    override suspend fun removeById(idPost: Long, idUser: Long) {
        dbQuery {
            Reactions.deleteWhere {
                (Reactions.user_id eq idUser).and(Reactions.post_id eq idPost)
            }
        }
    }


    override suspend fun isFirstReaction(item: ReactionModel): ReactionModel? =
        dbQuery {
            Reactions.select(
                where = (Reactions.user_id eq item.userId)
                    .and(Reactions.post_id eq item.postId)
            )
                .singleOrNull()?.toReactions()
        }
}