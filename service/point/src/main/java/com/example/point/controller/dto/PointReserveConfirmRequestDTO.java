package com.example.point.controller.dto;

import com.example.point.service.dto.PointReserveConfirmDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointReserveConfirmRequestDTO {

    public String requestId;

    public PointReserveConfirmDTO toPointReserveConfirmDTO() {
        return new PointReserveConfirmDTO(this.requestId);
    }

}
