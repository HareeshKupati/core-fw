package com.hbk.corefw.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private String message;
    private String field;
    private String value;
    private FieldLocation location;

    public Error(String message, String field, String value, FieldLocation location) {
        this.message = message;
        this.field = field;
        this.value = value;
        this.location = location;
    }

    public String getMessage() {
        return message;
    }

    public String getField() {
        return field;
    }

    public String getValue() {
        return value;
    }

    public FieldLocation getLocation() {
        return location;
    }
}
