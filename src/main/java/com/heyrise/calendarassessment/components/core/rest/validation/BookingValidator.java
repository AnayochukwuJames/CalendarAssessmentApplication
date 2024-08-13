package com.heyrise.calendarassessment.components.core.rest.validation;

import com.heyrise.calendarassessment.components.core.database.entity.Booking;
import com.heyrise.calendarassessment.components.core.database.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class BookingValidator {

    private final BookingRepository bookingRepository;

    public void validateBooking(Booking booking) {
        if (isOverlapping(booking)) {
            throw new IllegalArgumentException("Booking time overlaps with an existing booking.");
        }
        if (isOutsideAllowedHours(booking)) {
            throw new IllegalArgumentException("Booking time must be within the allowed hours (08:00 to 18:00).");
        }
    }

    private boolean isOverlapping(Booking booking) {
        return bookingRepository.existsByDateAndTimeRange(
                booking.getDate(), booking.getStartTime(), booking.getEndTime());
    }

    private boolean isOutsideAllowedHours(Booking booking) {
        LocalDate allowedStart = LocalDate.from(LocalTime.of(8, 0));
        LocalTime allowedEnd = LocalTime.of(18, 0);
        return booking.getStartTime().isBefore(LocalTime.from(allowedStart)) || booking.getEndTime().isAfter(allowedEnd);
    }
}
