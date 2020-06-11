package com.post.service

import com.post.dto.PostRequestDto
import com.post.dto.PostResponseDto
import com.post.model.PostModel
import com.post.repository.PostRepository
import io.ktor.features.NotFoundException
import java.util.*

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

        val time = Date().time
        val model = PostModel(
            id = input.id,
            ownerId = input.ownerId,
            author = input.author,
            createdDate = time,
            content = input.content,
            isApprove = input.isApprove,
            countApprove = input.countApprove,
            isNotApprove = input.isNotApprove,
            countNotApprove = input.countNotApprove,
            countRepost = input.countRepost,
            type = input.type,
            urlLink = input.urlLink,
            countViews = input.countViews,
            parentId = input.parentId,
            imageId = input.imageId
        )
        return PostResponseDto.fromModel(repo.save(model, ownerId))
    }

    suspend fun update(input: PostRequestDto, ownerId: Long): PostResponseDto {
        val time = Date().time
        val model = PostModel(
            id = input.id,
            ownerId = input.ownerId,
            author = input.author,
            createdDate = time,
            content = input.content,
            isApprove = input.isApprove,
            countApprove = input.countApprove,
            isNotApprove = input.isNotApprove,
            countNotApprove = input.countNotApprove,
            countRepost = input.countRepost,
            type = input.type,
            urlLink = input.urlLink,
            countViews = input.countViews,
            parentId = input.parentId,
            imageId = input.imageId
        )
        return PostResponseDto.fromModel(repo.update(model, ownerId))
    }

    suspend fun removeById(id: Long, ownerId: Long) {
        repo.removeById(id, ownerId)
    }

    suspend fun approveById(id: Long): PostResponseDto {
        val model = repo.approveById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun notApproveById(id: Long): PostResponseDto {
        val model = repo.notApproveById(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun unselectedApproves(id: Long): PostResponseDto {
        val model = repo.unselectedApproves(id) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }

    suspend fun repostById(id: Long, ownerId: Long): PostResponseDto {
        val time = Date().time
        val model = repo.repostById(id, ownerId, time) ?: throw NotFoundException()
        return PostResponseDto.fromModel(model)
    }


}