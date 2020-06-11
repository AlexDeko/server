package com.post.repository

import com.post.model.ReactionModel

interface ReactionRepository {
    suspend fun geAllByPostId(idPost: Long): List<ReactionModel>
    suspend fun getByIdPostAndIdUser(idPost: Long, idUser: Long): ReactionModel?
    suspend fun save(item: ReactionModel)
    suspend fun update(item: ReactionModel)
    suspend fun removeById(idPost: Long, idUser: Long)
    suspend fun isFirstReaction(item: ReactionModel): ReactionModel?
}