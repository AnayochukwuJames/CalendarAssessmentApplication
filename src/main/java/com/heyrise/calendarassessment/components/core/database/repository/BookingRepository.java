package com.heyrise.calendarassessment.components.core.database.repository;

import com.heyrise.calendarassessment.components.core.database.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.date = :date AND " +
            "((b.startTime <= :endTime AND b.endTime >= :startTime))")
    boolean existsByDateAndTimeRange(
            @Param("date") LocalDate date,
            @Param("startTime") LocalTime startTime,
            @Param("endTime") LocalTime endTime);

    @Query("SELECT COUNT(b) > 0 FROM Booking b WHERE b.date = :date AND (b.startTime < :endTime AND b.endTime > :startTime)")
    boolean existsByDateAndTimeOverlap(@Param("date") LocalDate date, @Param("startTime") LocalTime startTime, @Param("endTime") LocalTime endTime);

}
