package com.post.dto.schdule

data class LessonRequestDto(
    val name: String,
    val teacherName: String,
    val teacherId: Long,
    val classRoom: String,
    val timeStart: String,
    val timeEnd: String
)