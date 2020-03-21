package com.post.service

import com.post.dto.schdule.ScheduleDayDto
import com.post.dto.schdule.ScheduleDayRequestDto
import com.post.repository.ScheduleRepository

class ScheduleService(private val scheduleRepository: ScheduleRepository) {

    suspend fun getAll(): List<ScheduleDayDto> = scheduleRepository.getAll()

    suspend fun insert(schedule: ScheduleDayRequestDto): Unit = scheduleRepository.insert(schedule)
}