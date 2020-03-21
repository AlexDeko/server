package com.post.dto.schdule

data class ScheduleDayDto(
    val id: Long,
    val date: Long,
    val lessons: List<LessonDto>
)