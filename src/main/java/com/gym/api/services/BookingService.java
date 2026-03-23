package com.gym.api.services;

import com.gym.api.dtos.BookingRequest;
import com.gym.api.dtos.BookingResponse;


public interface BookingService {
    BookingResponse createBooking(BookingRequest bookingRequest);
}
