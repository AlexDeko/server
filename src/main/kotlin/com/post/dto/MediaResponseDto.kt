package com.post.dto

import com.post.model.MediaModel
import com.post.model.MediaType
import kotlinx.serialization.Serializable

@Serializable
data class MediaResponseDto(val id: String, val mediaType: MediaType) {
    companion object {
        fun fromModel(model: MediaModel) = MediaResponseDto(
            id = model.id,
            mediaType = model.mediaType
        )
    }
}