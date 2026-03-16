package com.example.point.controller.dto;

import com.example.point.service.dto.PointReserveCancelDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointReserveCancelRequestDTO {

    public String requestId;

    public PointReserveCancelDTO toPointReserveCancelDTO() {
        return new PointReserveCancelDTO(this.requestId);
    }

}
