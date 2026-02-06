package com.hbk.corefw.validation.field.annotation;

import com.hbk.corefw.validation.field.validator.NotNullValidator;

import java.lang.annotation.*;


@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Validate(by= NotNullValidator.class)
public @interface NotNull {
}
