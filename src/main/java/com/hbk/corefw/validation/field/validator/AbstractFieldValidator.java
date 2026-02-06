package com.hbk.corefw.validation.field.validator;

public abstract class AbstractFieldValidator<T> implements IFieldValidator<T> {
    String NOT_NULL = "%1$s is required.";
    String NOT_EMPTY = "%1$s is empty.";
    String LENGTH = "For %1$s,No of characters allowed is (min-max).";

}
