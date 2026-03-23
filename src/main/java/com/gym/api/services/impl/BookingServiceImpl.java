package com.gym.api.services.impl;

import com.gym.api.dtos.BookingRequest;
import com.gym.api.dtos.BookingResponse;
import com.gym.api.entity.Booking;
import com.gym.api.entity.GymClass;
import com.gym.api.entity.Member;
import com.gym.api.repository.BookingRepository;
import com.gym.api.repository.GymClassRepository;
import com.gym.api.repository.MemberRepository;
import com.gym.api.services.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final MemberRepository memberRepository;
    private final GymClassRepository gymClassRepository;

    public BookingServiceImpl(BookingRepository bookingRepository, MemberRepository memberRepository, GymClassRepository gymClassRepository) {
        this.bookingRepository = bookingRepository;
        this.memberRepository = memberRepository;
        this.gymClassRepository = gymClassRepository;
    }


    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Member member = memberRepository.findById(bookingRequest.memberId()).
                orElseThrow(() -> new RuntimeException(("Cliente no encontrado")));

        GymClass gymClass = gymClassRepository.findById(bookingRequest.classId()).
                orElseThrow(() -> new RuntimeException(("Gym Class no encontrado")));

        Long inscritos = bookingRepository.countByGymClass(gymClass.getId());
        if (inscritos >= gymClass.getCapacity()){
            throw new RuntimeException("Clase Llena");
        }
        Booking booking = new Booking();
        booking.setBookingDate(LocalDateTime.now());
        booking.setMemberId(member);
        booking.setClassId(gymClass);

        Booking saveBooking = bookingRepository.save(booking);

        return new BookingResponse(
                saveBooking.getId(),
                saveBooking.getBookingDate(),
                member.getName(),
                gymClass.getClassName()
        );
        }
}
