package com.post.repository

import com.post.db.data.post.Posts
import com.post.db.data.post.toPost
import com.post.db.dbQuery
import com.post.exception.ForbiddenException
import com.post.exception.NotFoundException
import com.post.model.PostModel
import com.post.model.PostType
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
            postId = Posts.insert { insertStatement ->
                insertStatement[Posts.ownerId] = ownerId
                insertStatement[author] = item.author
                insertStatement[createdDate] = item.createdDate
                insertStatement[content] = item.content
                insertStatement[isApprove] = item.isApprove
                insertStatement[countApprove] = item.countApprove
                insertStatement[isNotApprove] = item.isNotApprove
                insertStatement[countNotApprove] = item.countNotApprove
                insertStatement[countRepost] = item.countRepost
                insertStatement[type] = item.type.name
                insertStatement[urlLink] = item.urlLink
                insertStatement[countViews] = item.countViews + 1L
                insertStatement[parentId] = item.parentId
                insertStatement[imageId] = item.imageId
            }[Posts.id]

            Posts.select {
                Posts.id eq postId
            }.single().toPost()
        }

    override suspend fun update(item: PostModel, ownerId: Long): PostModel =
        dbQuery {
            Posts.update { updateStatement ->
                if (item.ownerId != ownerId) throw ForbiddenException("Нет прав доступа")
                updateStatement[id] = item.id
                updateStatement[Posts.ownerId] = item.ownerId
                updateStatement[author] = item.author
                updateStatement[createdDate] = item.createdDate
                updateStatement[content] = item.content
                updateStatement[isApprove] = item.isApprove
                updateStatement[countApprove] = item.countApprove
                updateStatement[isNotApprove] = item.isNotApprove
                updateStatement[countNotApprove] = item.countNotApprove
                updateStatement[countRepost] = item.countRepost
                updateStatement[type] = item.type.name
                updateStatement[urlLink] = item.urlLink
                updateStatement[countViews] = item.countViews
                updateStatement[parentId] = item.parentId
                updateStatement[imageId] = item.imageId
            }

            Posts.select {
                Posts.id eq item.id
            }.single().toPost()
        }


    override suspend fun removeById(id: Long, ownerId: Long) {
        if (getById(id)?.ownerId != ownerId) throw ForbiddenException("Нет прав доступа")
        dbQuery {
            Posts.deleteWhere { Posts.id eq id }
        }
    }

    override suspend fun approveById(id: Long): PostModel? =
        dbQuery {
            val post = Posts.select {
                Posts.id eq id
            }.singleOrNull()?.toPost() ?: throw NotFoundException("Нет такого поста")

            val isApproves = true
            val isNotApproves = false
            val countApproves = post.countApprove.inc()
            val countNotApproves = post.countNotApprove.dec()

            Posts.update(
                where = {
                    Posts.id eq id
                },
                body = { updateStatement ->
                    updateStatement[isApprove] = isApproves
                    updateStatement[countApprove] = countApproves
                    updateStatement[isNotApprove] = isNotApproves
                    updateStatement[countNotApprove] = countNotApproves
                }
            )

            post.copy(
                countApprove = countApproves, isApprove = isApproves, isNotApprove = isNotApproves,
                countNotApprove = countNotApproves
            )
        }

    override suspend fun notApproveById(id: Long): PostModel? =
        dbQuery {
            val post = Posts.select {
                Posts.id eq id
            }.singleOrNull()?.toPost() ?: throw NotFoundException("Нет такого поста")

            val isApproves = false
            val countApproves = post.countApprove.dec()
            val isNotApproves = true
            val countNotApproves = post.countNotApprove.inc()

            Posts.update(
                where = {
                    Posts.id eq id
                },
                body = { updateStatement ->
                    updateStatement[isApprove] = isApproves
                    updateStatement[countApprove] = countApproves
                    updateStatement[isNotApprove] = isNotApproves
                    updateStatement[countNotApprove] = countNotApproves
                }
            )

            post.copy(
                countApprove = countApproves, isApprove = isApproves, isNotApprove = isNotApproves,
                countNotApprove = countNotApproves
            )
        }

    override suspend fun unselectedApproves(id: Long): PostModel? =
        dbQuery {
            val post = Posts.select {
                Posts.id eq id
            }.singleOrNull()?.toPost() ?: throw NotFoundException("Нет такого поста")

            if (post.isNotApprove) {
                post.isNotApprove = false
                post.countNotApprove.dec()
                Posts.update(where = {
                    Posts.id eq post.id
                },
                    body = { updateStatement ->
                        updateStatement[isNotApprove] = post.isNotApprove
                        updateStatement[countNotApprove] = post.countNotApprove
                    }
                )
            } else {
                post.isApprove = false
                post.countApprove.dec()
                Posts.update(where = {
                    Posts.id eq post.id
                },
                    body = { updateStatement ->
                        updateStatement[isApprove] = post.isApprove
                        updateStatement[countApprove] = post.countApprove
                    }
                )
            }


            val result = Posts.select {
                Posts.id eq id
            }.singleOrNull()?.toPost()
            result
        }

    override suspend fun repostById(id: Long, ownerId: Long, time: Long): PostModel? =
        dbQuery {
            val originalPost = Posts.select {
                Posts.id eq id
            }.singleOrNull()?.toPost() ?: throw NotFoundException("Нет такого поста")

            val newId = Posts.insert { insertStatement ->
                insertStatement[Posts.ownerId] = ownerId
                insertStatement[author] = originalPost.author
                insertStatement[createdDate] = time
                insertStatement[content] = originalPost.content
                insertStatement[isApprove] = originalPost.isApprove
                insertStatement[countApprove] = originalPost.countApprove
                insertStatement[isNotApprove] = originalPost.isNotApprove
                insertStatement[countNotApprove] = originalPost.countNotApprove
                insertStatement[countRepost] = originalPost.countRepost
                insertStatement[type] = PostType.REPOST.name
                insertStatement[urlLink] = originalPost.urlLink
                insertStatement[countViews] = originalPost.countViews
                insertStatement[parentId] = originalPost.id
                insertStatement[imageId] = originalPost.imageId
            }[Posts.id]

            Posts.select {
                Posts.id eq newId
            }.single().toPost()
        }
}