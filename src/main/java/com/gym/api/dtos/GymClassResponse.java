package com.gym.api.dtos;

import com.gym.api.entity.enums.ActivityType;

import java.time.LocalDateTime;

public record GymClassResponse(
        Long id, ActivityType actType, String description, int capacity, int room, LocalDateTime startTime, LocalDateTime end, String instructorFullName
) {
}
