package com.post.service

import io.ktor.features.NotFoundException
import com.post.dto.PostRequestDto
import com.post.dto.PostResponseDto
import com.post.model.PostModel
import com.post.repository.PostRepository
import org.kodein.di.generic.instance
import org.kodein.di.ktor.kodein

class PostService(private val repo: PostRepository) {

    suspend fun getAll(): List<PostResponseDto> {
        return repo.getAll().map { PostResponseDto.fromModel(it) }
    }

    suspend fun getPage(id: Long, countPage: Int): List<PostResponseDto> {
        return repo.getAll().asSequence().map { PostResponseDto.fromModel(it) }
            .filter { it.id < id }.take(countPage).toList()
    }

    suspend fun getLastPage(countPage: Int): List<PostResponseDto> {
        return repo.getAll().asSequence().map { PostResponseDto.fromModel(it) }
            .take(countPage).toList()
    }

    suspend fun getById(id: Long): PostResponseDto {
        val model = repo.getById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun save(input: PostRequestDto, ownerId: Long): PostResponseDto {

        val model = PostModel(
            id = input.id,
            ownerId = input.ownerId,
            author = input.author,
            createdDate = input.createdDate,
            content = input.content,
            countLike = input.countLike,
            isLike = input.isLike,
            countRepost = input.countRepost,
            type = input.type,
            adsUrl = input.adsUrl,
            countViews = input.countViews,
            parentId = input.parentId,
            imageId = input.imageId,
            videoUrl = input.videoUrl,
            countComment = input.countComment,
            isCanCommented = input.isCanCommented,
            selectedLocation = input.selectedLocation
        )
        return PostResponseDto.fromModel(repo.save(model, ownerId))
    }

    suspend fun update(input: PostRequestDto, ownerId: Long): PostResponseDto {

        val model = PostModel(
            id = input.id,
            ownerId = input.ownerId,
            author = input.author,
            createdDate = input.createdDate,
            content = input.content,
            countLike = input.countLike,
            isLike = input.isLike,
            countRepost = input.countRepost,
            type = input.type,
            adsUrl = input.adsUrl,
            countViews = input.countViews,
            parentId = input.parentId,
            imageId = input.imageId,
            videoUrl = input.videoUrl,
            countComment = input.countComment,
            isCanCommented = input.isCanCommented,
            selectedLocation = input.selectedLocation
        )
        return PostResponseDto.fromModel(repo.update(model, ownerId))
    }

    suspend fun removeById(id: Long, ownerId: Long) {
        repo.removeById(id, ownerId)
    }

    suspend fun likedById(id: Long): PostResponseDto {
        val model = repo.likeById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun repostById(id: Long, ownerId: Long): PostResponseDto {
        val model = repo.repostById(id, ownerId) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun dislikeById(id: Long): PostResponseDto {
        val model = repo.dislikeById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }
}