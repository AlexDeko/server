package com.post.repository

import com.post.dto.schdule.ScheduleDayDto
import com.post.dto.schdule.ScheduleDayRequestDto

interface ScheduleRepository {

    suspend fun getAll(): List<ScheduleDayDto>
    suspend fun insert(item: ScheduleDayRequestDto)
}