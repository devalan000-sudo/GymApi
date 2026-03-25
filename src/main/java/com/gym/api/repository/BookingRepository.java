package com.gym.api.repository;

import com.gym.api.entity.Booking;
import com.gym.api.entity.GymClass;
import com.gym.api.entity.enums.BookingStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    Long countByGymClass(Long gymClassId);
    Long countByGymClassAndBookingStatus(Long gymClassId, BookingStatus bookingStatus);
    List<Booking> findByMemberIdAndStatus(Long memberId, BookingStatus bookingStatus);
}
