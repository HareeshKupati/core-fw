package com.hbk.corefw.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private Integer code;
    private String message;
    private String field;
    private String value;
    private FieldLocation location;

    public Error(Integer code, String message, String field, String value, FieldLocation location) {
        this.code = code;
        this.message = message;
        this.field = field;
        this.value = value;
        this.location = location;
    }

    public Integer getCode() {
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
