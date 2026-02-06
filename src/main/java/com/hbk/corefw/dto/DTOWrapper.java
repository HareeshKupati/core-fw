package com.hbk.corefw.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class DTOWrapper<DTO> {
    private DTO content;
    private List<Error> errors;

    public DTOWrapper(DTO content) {
        this.content = content;
    }

    public DTOWrapper(Error error) {
        this.errors = new ArrayList<>(1);
        errors.add(error);
    }

    public DTOWrapper(List<Error> errors) {
        this.errors = errors;
    }

    public DTO getContent() {
        return content;
    }

    public List<Error> getErrors() {
        return errors;
    }

}
