package com.post.repository

import com.post.dto.LikeDto
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import com.post.model.PostModel

class PostRepositoryInMemoryWithMutexImpl : PostRepository {
    private var nextId = 1L
    private val items = mutableListOf<PostModel>()
    private val mutex = Mutex()

    override suspend fun getAll(): List<PostModel> {
        mutex.withLock {
            return items.reversed()
        }
    }

    override suspend fun getById(id: Long): PostModel? {
        mutex.withLock {
            return items.find { it.id == id }
        }
    }

    override suspend fun save(item: PostModel): PostModel {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == item.id }) {
                -1 -> {
                    val copy = item.copy(id = nextId++)
                    items.add(copy)
                    copy
                }
                else -> {
                    val copy = items[index].copy(author = item.author, text = item.text)
                    items[index] = copy
                    copy
                }
            }
        }
    }

    override suspend fun removeById(id: Long) {
        mutex.withLock {
            items.removeIf { it.id == id }
        }
    }

    override suspend fun likeById(id: Long): PostModel? {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == id }) {
                -1 -> null
                else -> {
                    val item = items[index]
                    val copy = item.copy(like = LikeDto(item.like.count + 1L, true))
                    items[index] = copy
                    copy
                }
            }
        }
    }

    override suspend fun repostById(id: Long): PostModel? {
        mutex.withLock {
            val newItemRepost = items.find { it.id == id }?.copy(id = items.lastIndex + 1L, parentId = id)
            return newItemRepost
        }
    }

    override suspend fun dislikeById(id: Long): PostModel? {
        mutex.withLock {
            return when (val index = items.indexOfFirst { it.id == id }) {
                -1 -> null
                else -> {
                    val item = items[index]
                    val copy = item.copy(like = LikeDto(item.like.count - 1L, false))
                    items[index] = copy
                    copy
                }
            }
        }
    }
}

