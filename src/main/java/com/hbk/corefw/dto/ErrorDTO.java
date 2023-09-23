package com.hbk.corefw.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDTO {
    private String code;
    private String message;
    private String field;
    private String value;
    private FieldLocation location;

    public ErrorDTO(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorDTO(String code, String message, String field, FieldLocation location) {
        this.code = code;
        this.message = message;
        this.field = field;
        this.location = location;
    }

    public ErrorDTO(String code, String message, String field, String value, FieldLocation location) {
        this.code = code;
        this.message = message;
        this.field = field;
        this.value = value;
        this.location = location;
    }

    public String getCode() {
        return code;
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
