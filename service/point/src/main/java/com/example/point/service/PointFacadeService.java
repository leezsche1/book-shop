package com.example.point.service;

import com.example.point.service.dto.PointReserveCancelDTO;
import com.example.point.service.dto.PointReserveConfirmDTO;
import com.example.point.service.dto.PointReserveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PointFacadeService {

    private final PointService pointService;

    public void tryReserve(PointReserveDTO pointReserveDTO) {

        int count = 0;

        while (count < 3) {
            try {
                pointService.tryReserve(pointReserveDTO);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                count++;
            }
        }

        throw new RuntimeException("예약에 실패했어요. [낙관적 락]");

    }

    public void confirmReserve(PointReserveConfirmDTO pointReserveConfirmDTO) {

        int count = 0;

        while (count < 3) {
            try {
                pointService.confirmReserve(pointReserveConfirmDTO);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                count++;
            }
        }

        throw new RuntimeException("예약에 실패했어요. [낙관적 락]");
    }

    public void cancelReserve(PointReserveCancelDTO pointReserveCancelDTO) {
        int count = 0;

        while (count < 3) {
            try {
                pointService.cancelReserve(pointReserveCancelDTO);
                return;
            } catch (ObjectOptimisticLockingFailureException e) {
                count++;
            }
        }

        throw new RuntimeException("예약에 실패했어요. [낙관적 락]");
    }

}
