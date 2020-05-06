package com.post.dto

data class CommentDto(val count: Long,
                      val canPost: Boolean = false)