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

    suspend fun save(input: PostRequestDto, ownerId: Long): PostResponseDto {
        input.countViews += 1
        val model = PostModel(
            id = input.id,
            ownerId = input.ownerId,
            author = input.author,
            postType = input.postType,
            text = input.text,
            date = input.date,
            like = input.like,
            comment = input.comment,
            reply = input.reply,
            address = input.address,
            coordinates = input.coordinates,
            video = input.video,
            adsUrl = input.adsUrl,
            countViews = input.countViews
        )
        return PostResponseDto.fromModel(repo.save(model, ownerId))
    }

    suspend fun removeById(id: Long, ownerId: Long) {
        repo.removeById(id, ownerId)
    }

    suspend fun likedById(id: Long): PostResponseDto {
        val model = repo.likeById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun repostById(id: Long): PostResponseDto {
        val model = repo.repostById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun dislikeById(id: Long): PostResponseDto {
        val model = repo.dislikeById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }
}