package com.example.order.api.point.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointReserveApiRequestDTO {

    public String requestId;
    public Long memberId;
    public Long reserveAmount;

}
