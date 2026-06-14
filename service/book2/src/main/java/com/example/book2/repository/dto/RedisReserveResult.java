package com.example.book2.repository.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public enum RedisReserveResult {

    SUCCESS,
    OUT_OF_STOCK,
    NOT_INITIALIZED;

    public static RedisReserveResult from(Long result) {
        if (result == null) {
            throw new IllegalStateException("Redis Lua result is null");
        }

        if (result == 1L) {
            return SUCCESS;
        }

        if (result == 0L) {
            return OUT_OF_STOCK;
        }

        if (result == -1L) {
            return NOT_INITIALIZED;
        }

        throw new IllegalStateException("Unknown Redis result: " + result);
    }

}
