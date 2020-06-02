package com.post.repository

import com.post.model.ReactionModel

interface ReactionRepository {
    suspend fun geAllByPostId(id: Long): List<ReactionModel>
    suspend fun save(item: ReactionModel)
}