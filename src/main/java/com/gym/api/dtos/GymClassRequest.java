package com.gym.api.dtos;

import com.gym.api.entity.enums.ActivityType;

import java.time.LocalDateTime;

public record GymClassRequest(
        ActivityType actType, String description, int capacity, int room, LocalDateTime startTime, Integer durationMinutes, Long instructorId
) {
}
