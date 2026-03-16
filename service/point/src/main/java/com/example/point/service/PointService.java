package com.example.point.service;

import com.example.point.domain.Point;
import com.example.point.domain.PointReservation;
import com.example.point.repository.PointRepository;
import com.example.point.repository.PointReservationRepository;
import com.example.point.service.dto.PointReserveCancelDTO;
import com.example.point.service.dto.PointReserveConfirmDTO;
import com.example.point.service.dto.PointReserveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {

    private final PointRepository pointRepository;
    private final PointReservationRepository pointReservationRepository;

    @Transactional
    public void tryReserve(PointReserveDTO pointReserveDTO) {

        PointReservation reservation = pointReservationRepository.findByRequestId(pointReserveDTO.getRequestId());
        if (reservation != null) {
            System.out.println("이미 예약된 요청입니다.");
            return;
        }

        Point point = pointRepository.findByMemberId(pointReserveDTO.getMemberId());
        point.reserve(pointReserveDTO.getReserveAmount());

        pointReservationRepository.save(
                new PointReservation(
                        pointReserveDTO.getRequestId(),
                        point.getId(),
                        pointReserveDTO.getReserveAmount()
                )
        );

        pointRepository.save(point);

    }

    @Transactional
    public void confirmReserve(PointReserveConfirmDTO pointReserveConfirmDTO) {

        PointReservation reservation = pointReservationRepository.findByRequestId(pointReserveConfirmDTO.getRequestId());

        if (reservation == null) {
            throw new RuntimeException("예약된 정보가 없어요.");
        }

        if (reservation.getStatus() == PointReservation.PointReservationStatus.CONFIRMED) {
            System.out.println("이미 확정됐어요.");
            return;
        }

        Point point = pointRepository.findById(reservation.getPointId()).orElseThrow(
                () -> new RuntimeException("해당하는 포인트 내역이 없어요.")
        );

        point.confirm(reservation.getReservedAmount());
        reservation.confirm();

        pointRepository.save(point);
        pointReservationRepository.save(reservation);

    }

    @Transactional
    public void cancelReserve(PointReserveCancelDTO pointReserveCancelDTO) {

        PointReservation reservation = pointReservationRepository.findByRequestId(pointReserveCancelDTO.getRequestId());

        if (reservation == null) {
            throw new RuntimeException("예약된 정보가 없어요.");
        }

        if (reservation.getStatus() == PointReservation.PointReservationStatus.CANCELLED) {
            System.out.println("이미 취소된 예약입니다");
            return;
        }

        Point point = pointRepository.findById(reservation.getPointId()).orElseThrow(
                () -> new RuntimeException("해당하는 포인트 내역이 없어요.")
        );

        point.cancel(reservation.getReservedAmount());
        reservation.cancel();

        pointRepository.save(point);
        pointReservationRepository.save(reservation);

    }

}
