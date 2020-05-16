package com.post.repository

import com.post.model.PostModel

interface PostRepository {
    suspend fun getAll(): List<PostModel>
    suspend fun getById(id: Long): PostModel?
    suspend fun save(item: PostModel, ownerId: Long): PostModel
    suspend fun removeById(id: Long, ownerId: Long)
    suspend fun likeById(id: Long): PostModel?
    suspend fun dislikeById(id: Long): PostModel?
    suspend fun repostById(id: Long): PostModel?
}