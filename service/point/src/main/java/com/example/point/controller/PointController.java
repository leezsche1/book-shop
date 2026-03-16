package com.example.point.controller;

import com.example.point.controller.dto.PointReserveCancelRequestDTO;
import com.example.point.controller.dto.PointReserveRequestDTO;
import com.example.point.service.PointFacadeService;
import com.example.point.service.RedisLockService;
import com.example.point.controller.dto.PointReserveConfirmRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PointController {

    private final PointFacadeService pointFacadeService;
    private final RedisLockService redisLockService;

    @PostMapping("/point/reserve")
    public void reserve(@RequestBody PointReserveRequestDTO pointReserveRequestDTO) {

        String key = "point:" + pointReserveRequestDTO.getRequestId();

        boolean acquiredLock = redisLockService.tryLock(key, pointReserveRequestDTO.getRequestId());

        if (!acquiredLock) {
            throw new RuntimeException("락 획득에 실패했어요.");
        }

        try {
            pointFacadeService.tryReserve(pointReserveRequestDTO.toPointReserveDTO());
        } finally {
            redisLockService.releaseLock(key);
        }

    }

    @PostMapping("/point/confirm")
    public void confirm(@RequestBody PointReserveConfirmRequestDTO pointReserveConfirmRequestDTO) {

        String key = "point:" + pointReserveConfirmRequestDTO.getRequestId();
        boolean acquiredLock = redisLockService.tryLock(key, pointReserveConfirmRequestDTO.getRequestId());

        if (!acquiredLock) {
            throw new RuntimeException("락 획득에 실패했어요.");
        }

        try {
            pointFacadeService.confirmReserve(pointReserveConfirmRequestDTO.toPointReserveConfirmDTO());
        } finally {
            redisLockService.releaseLock(key);
        }

    }

    @PostMapping("/point/cancel")
    public void cancel(@RequestBody PointReserveCancelRequestDTO pointReserveCancelRequestDTO) {

        String key = "point:" + pointReserveCancelRequestDTO.getRequestId();
        boolean acquiredLock = redisLockService.tryLock(key, pointReserveCancelRequestDTO.getRequestId());
        if (!acquiredLock) {
            throw new RuntimeException("락 획득에 실패했어요.");
        }

        try {
            pointFacadeService.cancelReserve(pointReserveCancelRequestDTO.toPointReserveCancelDTO());
        } finally {
            redisLockService.releaseLock(key);
        }

    }

}
