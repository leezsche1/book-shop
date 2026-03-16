package com.example.point.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PointReserveDTO {

    public String requestId;
    public Long memberId;
    public Long reserveAmount;

}
