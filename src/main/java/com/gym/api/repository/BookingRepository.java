package com.gym.api.repository;

import com.gym.api.entity.Booking;
import com.gym.api.entity.GymClass;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Long countByGymClass(Long gymClassId);
}
