package com.post.repository


import com.post.model.token.firebase.TokenFirebaseModel

interface TokenFirebaseRepository {
    suspend fun getByIdUser(id: Long): TokenFirebaseModel?
    suspend fun getAllByIdUser(id: Long): List<TokenFirebaseModel>
    suspend fun save(item: TokenFirebaseModel)
    suspend fun update(item: TokenFirebaseModel)
    suspend fun removedById(id: Long)
}