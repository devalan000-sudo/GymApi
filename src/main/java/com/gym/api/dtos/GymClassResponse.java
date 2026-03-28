package com.gym.api.dtos;

import java.time.LocalDateTime;

public record GymClassResponse(
        String clasName, String description, int capacity, LocalDateTime startTime
) {
}
