package com.gym.api.controller;

import com.gym.api.dtos.BookingRequest;
import com.gym.api.dtos.BookingResponse;
import com.gym.api.entity.Booking;
import com.gym.api.services.BookingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("booking")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookingResponse> createBooking(@Valid @RequestBody BookingRequest request){
        BookingResponse response = bookingService.createBooking(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("{/id}")
    public ResponseEntity<BookingResponse> updateBooking(@PathVariable Long id, @RequestBody BookingRequest request){
        return ResponseEntity.ok(bookingService.updateBooking(id, request));
    }
}
