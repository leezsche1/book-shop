package com.example.point.repository;

import com.example.point.domain.PointReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PointReservationRepository extends JpaRepository<PointReservation, Long> {
    PointReservation findByRequestId(String requestId);
}

