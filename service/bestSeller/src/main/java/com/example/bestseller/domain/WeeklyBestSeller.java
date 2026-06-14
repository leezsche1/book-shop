package com.example.bestseller.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "weekly_best_seller",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_weekly_bestseller_period_rank", columnNames = {"period_start", "period_end", "rank_no"}),
                @UniqueConstraint(name = "uk_weekly_bestseller_period_book", columnNames = {"period_start", "period_end", "book_id"})
        })
public class WeeklyBestSeller {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "period_start", nullable = false)
    private LocalDate periodStart;
    @Column(name ="period_end", nullable = false)
    private LocalDate periodEnd;

    @Column(name = "book_id", nullable = false)
    private Long bookId;

    @Column(name = "rank_no", nullable = false)
    private Long rank;

    private Long salesCount;

    private LocalDateTime createdAt;

}
