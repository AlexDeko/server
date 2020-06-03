package com.post.repository

import com.post.dto.user.UserResponseDto
import com.post.model.UserModel

interface UserRepository {
    suspend fun getById(id: Long): UserModel?
    suspend fun getByIds(ids: Collection<Long>): List<UserModel>
    suspend fun getByUsername(username: String): UserModel?
    suspend fun save(item: UserModel)
    suspend fun update(id: Long, item: UserResponseDto)
}