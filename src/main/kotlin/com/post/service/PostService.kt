package com.post.service

import io.ktor.features.NotFoundException
import com.post.dto.PostRequestDto
import com.post.dto.PostResponseDto
import com.post.model.PostModel
import com.post.repository.PostRepository

class PostService(private val repo: PostRepository) {
    suspend fun getAll(): List<PostResponseDto> {
        return repo.getAll().map { PostResponseDto.fromModel(it) }
    }

    suspend fun getById(id: Long): PostResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun save(input: PostRequestDto): PostResponseDto {
        val model = PostModel(
            id = input.id,
            author = input.author,
            content = input.content
        )
        return PostResponseDto.fromModel(repo.save(model))
    }
}