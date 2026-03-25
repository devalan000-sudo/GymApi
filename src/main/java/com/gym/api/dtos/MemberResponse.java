package com.gym.api.dtos;

public record MemberResponse(
        Long id, String name, String lastname, String email, String address, String phone
) {
}
