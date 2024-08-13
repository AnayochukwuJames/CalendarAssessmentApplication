package com.heyrise.calendarassessment.components.core.service.serviceImp;

import com.heyrise.calendarassessment.common.exception.BookingOverlapException;
import com.heyrise.calendarassessment.common.exception.DateAlreadyBookedException;
import com.heyrise.calendarassessment.components.core.dataMapper.BookingMapper;
import com.heyrise.calendarassessment.components.core.database.entity.Booking;
import com.heyrise.calendarassessment.components.core.database.repository.BookingRepository;
import com.heyrise.calendarassessment.components.core.rest.dto.BookingDTO;
import com.heyrise.calendarassessment.components.core.rest.response.Response;
import com.heyrise.calendarassessment.components.core.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    private final BookingMapper bookingMapper;

    @Override
    @Transactional
    public ResponseEntity<Response> createBooking(BookingDTO bookingDTO) throws DateAlreadyBookedException, BookingOverlapException {
        LocalTime startTime = bookingDTO.getStartTime();
        LocalTime endTime = bookingDTO.getEndTime();
        if (startTime.isBefore(LocalTime.of(8, 0)) || endTime.isAfter(LocalTime.of(18, 0)) || startTime.isAfter(endTime)) {
            throw new IllegalArgumentException("Booking must be within the time range from 08:00 to 18:00 and end time must be after start time.");
        }
        boolean isOverlapping = bookingRepository.existsByDateAndTimeOverlap(bookingDTO.getDate(), startTime, endTime);
        if (isOverlapping) {
            throw new BookingOverlapException("The booking overlaps with an existing booking.");
        }

        Booking booking = bookingMapper.toEntity(bookingDTO);
        Booking savedBooking = bookingRepository.save(booking);

        Map<String, Object> map = new HashMap<>();
        map.put("booking", bookingMapper.toDTO(savedBooking));
        Response response = new Response(true, "Booking created successfully", map);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<Response> updateBooking(Long id, BookingDTO bookingDTO) {
        Booking booking = bookingMapper.toEntity(bookingDTO);
        booking.setId(id);
        Booking updatedBooking = bookingRepository.save(booking);
        Map<String, Object> map = new HashMap<>();
        map.put("booking", bookingMapper.toDTO(updatedBooking));
        Response response = new Response(true, "Booking updated successfully", map);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Response> deleteBooking(Long id) {
        try {
            bookingRepository.deleteById(id);
            Response response = new Response(true, "Booking deleted successfully", null);
            return ResponseEntity.ok(response);
        } catch (NoSuchElementException exception) {
            Response response = new Response(false, "Booking not found.", null);
            return ResponseEntity.status(404).body(response);
        }
    }

    @Override
    public ResponseEntity<Response> getBooking(Long id) {
        Optional<Booking> optionalBooking = bookingRepository.findById(id);
        if (!optionalBooking.isPresent()) {
            Response response = new Response(false, "Booking not found.", null);
            return ResponseEntity.status(404).body(response);
        }
        Booking booking = optionalBooking.get();
        Map<String, Object> map = new HashMap<>();
        map.put("booking", bookingMapper.toDTO(booking));
        Response response = new Response(true, "Booking found", map);
        return ResponseEntity.ok(response);
    }

    @Override
    public List<BookingDTO> getAllBookings() {
        List<BookingDTO> bookings = bookingRepository.findAll().stream()
                .map(bookingMapper::toDTO)
                .collect(Collectors.toList());
        Map<String, Object> map = new HashMap<>();
        map.put("bookings", bookings);
        Response response = new Response(true, "All bookings retrieved successfully", map);
        return (List<BookingDTO>) ResponseEntity.ok(response);
    }
}
