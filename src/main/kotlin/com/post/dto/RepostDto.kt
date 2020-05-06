package com.post.dto

import java.util.*

data class RepostDto(
    val count: Long,
    val text: String? = null,
    val author: String? = null,
    val date: String? = null
)