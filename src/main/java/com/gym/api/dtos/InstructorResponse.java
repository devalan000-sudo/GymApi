package com.gym.api.dtos;

import com.gym.api.entity.enums.ActivityType;

public record InstructorResponse(
        Long id, String firstname,  String lastname, String email, ActivityType speciality, String phone
) {
}
