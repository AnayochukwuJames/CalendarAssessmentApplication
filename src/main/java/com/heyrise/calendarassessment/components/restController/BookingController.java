package com.heyrise.calendarassessment.components.restController;

import com.heyrise.calendarassessment.common.exception.DateAlreadyBookedException;
import com.heyrise.calendarassessment.components.core.database.entity.Booking;
import com.heyrise.calendarassessment.components.core.rest.dto.BookingDTO;
import com.heyrise.calendarassessment.components.core.rest.response.Response;
import com.heyrise.calendarassessment.components.core.service.BookingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @PostMapping("/create")
    public ResponseEntity<Response> createBooking(@RequestBody @Valid BookingDTO bookingDTO) throws DateAlreadyBookedException {
        return bookingService.createBooking(bookingDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getBooking(@PathVariable Long id) {
        return bookingService.getBooking(id);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Response> updateBooking(@PathVariable Long id, @RequestBody @Valid BookingDTO bookingDTO) {
        return bookingService.updateBooking(id, bookingDTO);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteBooking(@PathVariable Long id) {
        return bookingService.deleteBooking(id);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return (ResponseEntity<List<Booking>>) bookingService.getAllBookings();

    }
}
