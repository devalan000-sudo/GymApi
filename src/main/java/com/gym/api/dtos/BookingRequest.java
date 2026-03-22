package com.gym.api.dtos;


import java.time.LocalDateTime;

public record BookingRequest(
        LocalDateTime bookingDate, Long memberId, Long classId
) {
}
