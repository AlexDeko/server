package com.post.repository

import com.post.model.PostModel

interface PostRepository {
    suspend fun getAll(): List<PostModel>
    suspend fun getById(id: Long): PostModel?
    suspend fun save(item: PostModel, ownerId: Long): PostModel
    suspend fun update(item: PostModel, ownerId: Long): PostModel
    suspend fun removeById(id: Long, ownerId: Long)
    suspend fun approveById(id: Long): PostModel?
    suspend fun notApproveById(id: Long): PostModel?
    suspend fun unselectedApproves(id: Long): PostModel?
    suspend fun repostById(id: Long, ownerId: Long, time: Long): PostModel?
}