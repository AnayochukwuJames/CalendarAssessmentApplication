package com.heyrise.calendarassessment.components.core.service;

import com.heyrise.calendarassessment.common.exception.DateAlreadyBookedException;
import com.heyrise.calendarassessment.components.core.rest.dto.BookingDTO;
import com.heyrise.calendarassessment.components.core.rest.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface BookingService {
    ResponseEntity<Response> createBooking(BookingDTO bookingDTO) throws DateAlreadyBookedException;
    ResponseEntity<Response> updateBooking(Long id, BookingDTO bookingDTO);
    ResponseEntity<Response> deleteBooking(Long id);
    ResponseEntity<Response> getBooking(Long id);
    List<BookingDTO> getAllBookings();
}
