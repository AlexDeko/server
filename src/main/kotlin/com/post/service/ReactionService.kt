package com.post.service

import com.post.dto.reaction.ReactionResponseDto
import com.post.dto.reaction.toModel
import com.post.model.ReactionModel
import com.post.model.toDto
import com.post.repository.ReactionRepository

class ReactionService(private val repo: ReactionRepository) {

    suspend fun geAllByPostId(id: Long): List<ReactionModel> {
        return repo.geAllByPostId(id)
    }

    suspend fun getByIdPostAndIdUser(idPost: Long, idUser: Long): ReactionResponseDto? {
        val reaction = repo.getByIdPostAndIdUser(idPost = idPost, idUser = idUser)
        return reaction?.toDto() ?: return null
    }

    suspend fun saveReaction(item: ReactionResponseDto) {
        repo.save(item = item.toModel())
    }

    suspend fun update(item: ReactionResponseDto) {
        repo.update(item = item.toModel())
    }

    suspend fun removeById(idPost: Long, idUser: Long) {
        removeById(idPost = idPost, idUser = idUser)
    }

    suspend fun saveOrUpdateReaction(item: ReactionResponseDto) {
        val result = repo.isFirstReaction(item = item.toModel())
        if (result != null) {
            repo.update(item = item.toModel())
        } else repo.save(item = item.toModel())

    }

}