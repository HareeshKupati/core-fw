package com.hbk.corefw.validation.field.annotation;

import com.hbk.corefw.validation.field.validator.IFieldValidator;

import java.lang.annotation.*;


@Target({ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Validate {
    Class<? extends IFieldValidator> by();
}
