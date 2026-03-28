package com.gym.api.entity;

import com.gym.api.entity.enums.ActivityType;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "gym_classes")
public class GymClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Enumerated(EnumType.STRING)
    private ActivityType actType;
    private String description;
    private int capacity;
    private int room;
    private LocalDateTime startTime;
    private Integer durationMinutes;
    private boolean active;
    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private Instructor instructor;
}
