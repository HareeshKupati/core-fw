package com.hbk.corefw.validation.field.annotation;

import com.hbk.corefw.validation.field.validator.NotEmptyValidator;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Validate(by = NotEmptyValidator.class)
public @interface NotEmpty {
    public int minLength() default 1;

    public int maxLength() default 30;
}
