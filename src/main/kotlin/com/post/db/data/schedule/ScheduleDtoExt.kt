package com.post.db.data.schedule

import org.jetbrains.exposed.sql.ResultRow
import com.post.dto.schdule.LessonDto
import com.post.dto.schdule.ScheduleDayDto

fun ResultRow.toScheduleDayDto(lessons: List<LessonDto>) = ScheduleDayDto(
    id = this[Schedule.id],
    date = this[Schedule.date],
    lessons = lessons
)