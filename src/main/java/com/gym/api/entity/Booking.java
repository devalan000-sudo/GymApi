package com.gym.api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private LocalDateTime bookingDate;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member memberId;
    @ManyToOne
    @JoinColumn(name = "class_id")
    private GymClass classId;


}
