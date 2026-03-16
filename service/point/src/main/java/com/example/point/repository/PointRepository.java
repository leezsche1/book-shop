package com.example.point.repository;

import com.example.point.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<Point, Long> {

    Point findByMemberId(Long memberId);

}
