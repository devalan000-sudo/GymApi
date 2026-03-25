package com.gym.api.services;

import com.gym.api.dtos.BookingRequest;
import com.gym.api.dtos.BookingResponse;

import java.util.List;


public interface BookingService {
    List<BookingResponse> findAllBooking(BookingRequest bookingRequest);
    BookingResponse createBooking(BookingRequest bookingRequest);
    BookingResponse updateBooking(Long id, BookingRequest bookingRequest);
    void cancellAllFuturesBookings(Long memberId);
    void deleteBooking(Long id);
}
