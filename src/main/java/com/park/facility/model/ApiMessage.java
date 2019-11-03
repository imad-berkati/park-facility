package com.park.facility.model;

import lombok.Data;

/**
 * This object is used to provide (code, message) data, like errors and warning
 *
 * @author Imad Berkati
 */

@Data
public class ApiMessage {

    /**
     * Api message code
     */
    private String code;

    /**
     * Api message
     */
    private String message;

    public ApiMessage() {

    }

    public ApiMessage(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
