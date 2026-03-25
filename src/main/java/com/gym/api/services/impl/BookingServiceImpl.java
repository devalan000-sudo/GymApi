package com.gym.api.services.impl;

import com.gym.api.dtos.BookingRequest;
import com.gym.api.dtos.BookingResponse;
import com.gym.api.entity.Booking;
import com.gym.api.entity.GymClass;
import com.gym.api.entity.Member;
import com.gym.api.entity.enums.BookingStatus;
import com.gym.api.repository.BookingRepository;
import com.gym.api.repository.GymClassRepository;
import com.gym.api.repository.MemberRepository;
import com.gym.api.services.BookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
    public List<BookingResponse> findAllBooking(BookingRequest bookingRequest) {
        return bookingRepository.findAll().stream().map(
            booking -> new BookingResponse(
                    booking.getId(),
                    booking.getBookingDate(),
                    booking.getMemberId().getName(),
                    booking.getMemberId().getName()
            )
        ).toList();
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

    @Override
    public BookingResponse updateBooking(Long id, BookingRequest bookingRequest) {
        Booking booking = bookingRepository.findById(id).orElseThrow(() -> new RuntimeException(("Cliente no encontrado")));
        Member member = memberRepository.findById(bookingRequest.memberId()).orElseThrow(() -> new RuntimeException(("Cliente no encontrado")));
        GymClass gymClass = gymClassRepository.findById(bookingRequest.classId()).orElseThrow(() -> new RuntimeException(("Clase no encontrada")));

        if(!booking.getClassId().getId().equals(gymClass.getId())){
            Long inscritos = bookingRepository.countByGymClass(gymClass.getId());
            if(inscritos >= gymClass.getCapacity()){
                throw new RuntimeException("Clase Llena");
            }
        }
        booking.setMemberId(member);
        booking.setClassId(gymClass);
        booking.setBookingDate(LocalDateTime.now());

        Booking updateBooking = bookingRepository.save(booking);

        return new BookingResponse(
                updateBooking.getId(),
                updateBooking.getBookingDate(),
                member.getName(),
                gymClass.getClassName()
        );
    }

    @Override
    public void cancellAllFuturesBookings(Long memberId) {
        List<Booking> activeBookings = bookingRepository.findByMemberIdAndStatus(memberId, BookingStatus.RESERVED);
        if (!activeBookings.isEmpty()) {
            activeBookings.forEach(booking ->
                    booking.setBookingStatus(BookingStatus.CANCELLED));
        }
        bookingRepository.saveAll(activeBookings);
    }

    @Override
    public void deleteBooking(Long id) {
        if (bookingRepository.existsById(id)) {
            bookingRepository.deleteById(id);
        }

    }
}
