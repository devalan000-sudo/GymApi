package com.gym.api.dtos;

import java.time.LocalDateTime;

public record BookingResponse(Long id, LocalDateTime bookingDate, String memberName, String gymClass) {
}
