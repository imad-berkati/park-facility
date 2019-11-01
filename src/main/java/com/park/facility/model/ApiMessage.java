package com.park.facility.model;

import lombok.Data;

@Data
public class ApiMessage {

    private String code;

    private String message;

    public ApiMessage() {

    }

    public ApiMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
