package com.heyrise.calendarassessment.components.core.dataMapper;

import org.mapstruct.Mapper;
import com.heyrise.calendarassessment.components.core.database.entity.Booking;
import com.heyrise.calendarassessment.components.core.rest.dto.BookingDTO;

@Mapper(componentModel = "spring")
public interface BookingMapper {
    BookingDTO toDTO(Booking booking);
    Booking toEntity(BookingDTO bookingDTO);
}
