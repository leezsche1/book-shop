package com.example.book.repository;

import com.example.book.domain.BookReservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookReservationRepository extends JpaRepository<BookReservation, Long> {

    List<BookReservation> findAllByRequestId(String orderId);

}
