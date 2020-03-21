package com.post.dto.schdule

data class ScheduleDayRequestDto(
    val date: String,
    val lessons: List<LessonRequestDto>
)