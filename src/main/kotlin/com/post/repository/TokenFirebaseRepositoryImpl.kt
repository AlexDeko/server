package com.post.repository

import com.post.db.data.token.fairbase.TokensFirebase
import com.post.db.data.token.fairbase.toTokenFirebase
import com.post.db.dbQuery
import com.post.model.token.firebase.TokenFirebaseModel
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update

class TokenFirebaseRepositoryImpl : TokenFirebaseRepository {
    override suspend fun getByIdUser(id: Long): TokenFirebaseModel? =
        dbQuery {
            TokensFirebase.select {
                TokensFirebase.user_id eq id
            }.singleOrNull()?.toTokenFirebase()
        }


    override suspend fun getAllByIdUser(id: Long): List<TokenFirebaseModel> =
        dbQuery {
            TokensFirebase.select {
                TokensFirebase.user_id eq id
            }.map {
                it.toTokenFirebase()
            }
        }

    override suspend fun save(item: TokenFirebaseModel) {
        dbQuery {
            TokensFirebase.insert { insertStatement ->
                insertStatement[user_id] = item.userId
                insertStatement[token] = item.token
            }
        }
    }

    override suspend fun update(item: TokenFirebaseModel) {
        dbQuery {
            TokensFirebase.update(
                where = {
                    TokensFirebase.id eq item.id
                },
                body = { updateStatement ->
                    updateStatement[user_id] = item.userId
                    updateStatement[token] = item.token
                }
            )
        }
    }

    override suspend fun removedById(id: Long) {
        dbQuery {
            TokensFirebase.deleteWhere {
                TokensFirebase.id eq id
            }
        }
    }

}

