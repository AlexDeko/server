package com.post.service

import com.post.model.ReactionModel
import com.post.repository.ReactionRepository

class ReactionService(private val repo: ReactionRepository) {

    suspend fun geAllByPostId(id: Long): List<ReactionModel> {
        return repo.geAllByPostId(id)
    }

    suspend fun saveReaction(item: ReactionModel) {
        repo.save(item = item)
    }
}