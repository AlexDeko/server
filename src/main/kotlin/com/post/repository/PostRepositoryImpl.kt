package com.post.repository

import com.post.db.data.post.Posts
import com.post.db.data.post.toPost
import com.post.db.dbQuery
import com.post.model.PostModel
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

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

    override suspend fun save(item: PostModel, ownerId: Long): PostModel {
        dbQuery {
            when(item.id){
                0L -> Posts.insert { insertStatement ->
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
                else -> Posts.update { insertStatement ->
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
                    insertStatement[Posts.countViews] = item.countViews
                    insertStatement[Posts.parentId] = item.parentId
                    insertStatement[Posts.imageId] = item.imageId
                    insertStatement[Posts.videoUrl] = item.videoUrl
                    insertStatement[Posts.countComment] = item.countComment
                    insertStatement[Posts.isCanCommented] = item.isCanCommented
                    insertStatement[Posts.selectedLocation] = item.selectedLocation
                }
            }
        }
        TODO("Not yet implemented")
    }

    override suspend fun removeById(id: Long, ownerId: Long) {
        dbQuery {

        }
        TODO("Not yet implemented")
    }

    override suspend fun likeById(id: Long): PostModel? {
        dbQuery {

        }
        TODO("Not yet implemented")
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        dbQuery {

        }
        TODO("Not yet implemented")
    }

    override suspend fun repostById(id: Long): PostModel? {
        dbQuery {

        }
        TODO("Not yet implemented")
    }
}