package com.post.repository

import com.post.db.data.post.Posts
import com.post.db.data.post.toPost
import com.post.db.dbQuery
import com.post.exception.ForbiddenException
import com.post.exception.NotFoundException
import com.post.model.PostModel
import org.jetbrains.exposed.sql.*

class PostRepositoryImpl : PostRepository {
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

    override suspend fun save(item: PostModel, ownerId: Long): PostModel =
        dbQuery {
            val postId: Long
            when (item.id) {
                0L -> {
                    postId = Posts.insert { insertStatement ->
                        insertStatement[Posts.ownerId] = ownerId
                        insertStatement[author] = item.author
                        insertStatement[createdDate] = item.createdDate
                        insertStatement[content] = item.content
                        insertStatement[countLike] = item.countLike
                        insertStatement[isLike] = item.isLike
                        insertStatement[countRepost] = item.countRepost
                        insertStatement[type] = item.type
                        insertStatement[adsUrl] = item.adsUrl
                        insertStatement[countViews] = item.countViews + 1L
                        insertStatement[parentId] = item.parentId
                        insertStatement[imageId] = item.imageId
                        insertStatement[videoUrl] = item.videoUrl
                        insertStatement[countComment] = item.countComment
                        insertStatement[isCanCommented] = item.isCanCommented
                        insertStatement[selectedLocation] = item.selectedLocation
                    }[Posts.id]
                }

                else -> {
                    postId = item.id
                    Posts.update { updateStatement ->
                        if (item.ownerId != ownerId) throw ForbiddenException("Нет прав доступа")
                        updateStatement[id] = item.id
                        updateStatement[Posts.ownerId] = item.ownerId
                        updateStatement[author] = item.author
                        updateStatement[createdDate] = item.createdDate
                        updateStatement[content] = item.content
                        updateStatement[countLike] = item.countLike
                        updateStatement[isLike] = item.isLike
                        updateStatement[countRepost] = item.countRepost
                        updateStatement[type] = item.type
                        updateStatement[adsUrl] = item.adsUrl
                        updateStatement[countViews] = item.countViews
                        updateStatement[parentId] = item.parentId
                        updateStatement[imageId] = item.imageId
                        updateStatement[videoUrl] = item.videoUrl
                        updateStatement[countComment] = item.countComment
                        updateStatement[isCanCommented] = item.isCanCommented
                        updateStatement[selectedLocation] = item.selectedLocation
                    }
                }
            }

            Posts.select {
                Posts.id eq postId
            }.single().toPost()
        }


    override suspend fun removeById(id: Long, ownerId: Long) {
        if (getById(id)?.ownerId != ownerId) throw ForbiddenException("Нет прав доступа")
        dbQuery {
            Posts.deleteWhere { Posts.id eq id }
        }
    }

    override suspend fun likeById(id: Long): PostModel? =
        dbQuery {
            val post = Posts.select {
                Posts.id eq id
            }.singleOrNull()?.toPost() ?: throw NotFoundException("Нет такого поста")

            val likes = post.countLike.inc()

            Posts.update(
                where = {
                    Posts.id eq id
                },
                body = { updateStatement ->
                    updateStatement[countLike] = likes
                }
            )

            post.copy(countLike = likes)
        }

    override suspend fun dislikeById(id: Long): PostModel? =
        dbQuery {
            val post = Posts.select {
                Posts.id eq id
            }.singleOrNull()?.toPost() ?: throw NotFoundException("Нет такого поста")

            val likes = post.countLike.dec()

            Posts.update(
                where = {
                    Posts.id eq id
                },
                body = { updateStatement ->
                    updateStatement[countLike] = likes
                }
            )

            post.copy(countLike = likes)
        }

    override suspend fun repostById(id: Long): PostModel? =
        dbQuery {
            val originalPost = Posts.select {
                Posts.id eq id
            }.singleOrNull()?.toPost() ?: throw NotFoundException("Нет такого поста")

            val newId = Posts.insert { insertStatement ->
                // TODO Может тут автором должен стать человек, который сделал репост?
                insertStatement[ownerId] = originalPost.ownerId
                insertStatement[author] = originalPost.author
                insertStatement[createdDate] = originalPost.createdDate
                insertStatement[content] = originalPost.content
                insertStatement[countLike] = originalPost.countLike
                insertStatement[isLike] = originalPost.isLike
                insertStatement[countRepost] = originalPost.countRepost
                insertStatement[type] = originalPost.type
                insertStatement[adsUrl] = originalPost.adsUrl
                insertStatement[countViews] = originalPost.countViews
                insertStatement[parentId] = originalPost.id
                insertStatement[imageId] = originalPost.imageId
                insertStatement[videoUrl] = originalPost.videoUrl
                insertStatement[countComment] = originalPost.countComment
                insertStatement[isCanCommented] = originalPost.isCanCommented
                insertStatement[selectedLocation] = originalPost.selectedLocation
            }[Posts.id]

            Posts.select {
                Posts.id eq newId
            }.single().toPost()
        }
}