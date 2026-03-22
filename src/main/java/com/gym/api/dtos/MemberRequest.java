package com.gym.api.dtos;

import jakarta.validation.constraints.NotBlank;

public record MemberRequest(
        @NotBlank(message = "El nombre es obligatorio")
        String name,
        @NotBlank(message = "El apellido es obligatorio")
        String lastname,
        @NotBlank(message = "El email es obligatorio")
        String email,
        @NotBlank(message = "La direccion es obligatoria")
        String address,

        String phone
) {
}
