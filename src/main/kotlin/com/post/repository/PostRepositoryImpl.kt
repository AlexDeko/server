package com.post.repository

import com.post.db.data.post.Posts
import com.post.db.data.post.toPost
import com.post.db.dbQuery
import com.post.exception.ForbiddenException
import com.post.exception.NotFoundException
import com.post.model.PostModel
import org.jetbrains.exposed.sql.*

class PostRepositoryImpl: PostRepository {
    override suspend fun getAll(): List<PostModel> =
        dbQuery {
            Posts.selectAll().toList().map {
                it.toPost()
            }
        }

    override suspend fun getById(id: Long): PostModel? =
        dbQuery {
            Posts.select {
                (Posts.id eq id)
            }.singleOrNull()?.toPost()
        }

    override suspend fun save(item: PostModel, ownerId: Long) {
        dbQuery {
            when (item.id) {
                0L -> Posts.insert { insertStatement ->
                    insertStatement[Posts.id] = item.id
                    insertStatement[Posts.ownerId] = ownerId
                    insertStatement[Posts.author] = item.author
                    insertStatement[Posts.createdDate] = item.createdDate
                    insertStatement[Posts.content] = item.content
                    insertStatement[Posts.countLike] = item.countLike
                    insertStatement[Posts.isLike] = item.isLike
                    insertStatement[Posts.countRepost] = item.countRepost
                    insertStatement[Posts.type] = item.type
                    insertStatement[Posts.adsUrl] = item.adsUrl
                    insertStatement[Posts.countViews] = item.countViews + 1L
                    insertStatement[Posts.parentId] = item.parentId
                    insertStatement[Posts.imageId] = item.imageId
                    insertStatement[Posts.videoUrl] = item.videoUrl
                    insertStatement[Posts.countComment] = item.countComment
                    insertStatement[Posts.isCanCommented] = item.isCanCommented
                    insertStatement[Posts.selectedLocation] = item.selectedLocation
                }
                else -> Posts.update { updateStatement ->
                    if (item.ownerId != ownerId) throw ForbiddenException("Нет прав доступа")
                    updateStatement[Posts.id] = item.id
                    updateStatement[Posts.ownerId] = item.ownerId
                    updateStatement[Posts.author] = item.author
                    updateStatement[Posts.createdDate] = item.createdDate
                    updateStatement[Posts.content] = item.content
                    updateStatement[Posts.countLike] = item.countLike
                    updateStatement[Posts.isLike] = item.isLike
                    updateStatement[Posts.countRepost] = item.countRepost
                    updateStatement[Posts.type] = item.type
                    updateStatement[Posts.adsUrl] = item.adsUrl
                    updateStatement[Posts.countViews] = item.countViews
                    updateStatement[Posts.parentId] = item.parentId
                    updateStatement[Posts.imageId] = item.imageId
                    updateStatement[Posts.videoUrl] = item.videoUrl
                    updateStatement[Posts.countComment] = item.countComment
                    updateStatement[Posts.isCanCommented] = item.isCanCommented
                    updateStatement[Posts.selectedLocation] = item.selectedLocation
                }
            }
        }
    }


    override suspend fun removeById(id: Long, ownerId: Long) {
        if (getById(id)?.ownerId != ownerId) throw ForbiddenException("Нет прав доступа")
        dbQuery {
            Posts.deleteWhere { Posts.id eq id }
        }
    }

    override suspend fun likeById(id: Long): PostModel? {
        dbQuery {
            Posts.update {

            }
        }
        TODO("Not yet implemented")
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        dbQuery {

            Posts.update {
            }
        }
        TODO("Not yet implemented")
    }

    override suspend fun repostById(id: Long): PostModel? {
        val item = getById(id) ?: throw NotFoundException("Пост не найден")
        dbQuery {
            Posts.insert { insertStatement ->
                insertStatement[Posts.id] = item.id
                insertStatement[Posts.ownerId] = item.ownerId
                insertStatement[Posts.author] = item.author
                insertStatement[Posts.createdDate] = item.createdDate
                insertStatement[Posts.content] = item.content
                insertStatement[Posts.countLike] = item.countLike
                insertStatement[Posts.isLike] = item.isLike
                insertStatement[Posts.countRepost] = item.countRepost
                insertStatement[Posts.type] = item.type
                insertStatement[Posts.adsUrl] = item.adsUrl
                insertStatement[Posts.countViews] = item.countViews + 1L
                insertStatement[Posts.parentId] = item.parentId
                insertStatement[Posts.imageId] = item.imageId
                insertStatement[Posts.videoUrl] = item.videoUrl
                insertStatement[Posts.countComment] = item.countComment
                insertStatement[Posts.isCanCommented] = item.isCanCommented
                insertStatement[Posts.selectedLocation] = item.selectedLocation
            }
            Posts.select { Posts.id eq id }
        }
        TODO("Not yet implemented")
    }
}