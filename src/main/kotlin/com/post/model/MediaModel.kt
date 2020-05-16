package com.post.model

import kotlinx.serialization.Serializable

enum class MediaType {
    IMAGE
}

@Serializable
data class MediaModel(val id: String, val mediaType: MediaType)