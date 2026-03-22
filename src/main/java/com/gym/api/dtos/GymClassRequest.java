package com.gym.api.dtos;

import java.time.LocalDateTime;

public record GymClassRequest(
    String className, String description, int capacity, LocalDateTime startTime
) {
}
