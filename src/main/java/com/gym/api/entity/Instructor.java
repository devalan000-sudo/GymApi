package com.gym.api.entity;

import com.gym.api.entity.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "instructors")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    @Column(unique = true)
    private String email;
    @Enumerated(EnumType.STRING)
    private ActivityType speciality;
    private String phone;
    private boolean active = true;
}
