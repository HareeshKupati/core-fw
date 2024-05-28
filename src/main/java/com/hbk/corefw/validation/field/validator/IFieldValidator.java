package com.hbk.corefw.validation.field.validator;

import javafx.util.Pair;

public interface IFieldValidator<T> {

    //if validation passes returns true else  returns false and error message
    Pair<Boolean, String> validate(T annotation, Object value);
}
