package com.heyrise.calendarassessment.components.core.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class BookingDTO {

   private Long id;

    @NotNull(message = "Date cannot be null.")
    private LocalDate date;

    @NotNull(message = "Start time cannot be null.")
    private LocalTime startTime;

    @NotNull(message = "End time cannot be null.")
    private LocalTime endTime;
}
