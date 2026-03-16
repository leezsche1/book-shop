package com.example.point.controller.dto;

import com.example.point.service.dto.PointReserveDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointReserveRequestDTO {

    public String requestId;
    public Long memberId;
    public Long reserveAmount;

    public PointReserveDTO toPointReserveDTO() {
        return new PointReserveDTO(
                this.requestId,
                this.memberId,
                this.reserveAmount
        );
    }

}
