package com.gym.api.dtos;

import com.gym.api.entity.enums.ActivityType;

public record InstructorRequest(
        String firstname, String lastname, String email, ActivityType speciality, String phone
) {
}
