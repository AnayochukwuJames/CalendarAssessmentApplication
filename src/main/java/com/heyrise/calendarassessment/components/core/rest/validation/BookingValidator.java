package com.heyrise.calendarassessment.components.core.rest;

import com.heyrise.calendarassessment.components.core.database.entity.Booking;
import com.heyrise.calendarassessment.components.core.database.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookingValidator {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingValidator(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public void validateBooking(Booking booking) {
        // Validation logic for overlapping bookings or other business rules
        if (isOverlapping(booking)) {
            throw new IllegalArgumentException("Booking time overlaps with an existing booking.");
        }
        // Additional validation logic can be added here
    }

    private boolean isOverlapping(Booking booking) {
        // Check if the booking overlaps with any existing bookings
        return bookingRepository.existsByDateAndTimeRange(
                booking.getDate(), booking.getStartTime(), booking.getEndTime());
    }
}
