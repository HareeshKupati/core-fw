package com.hbk.corefw.validation.field.validator;

import com.hbk.corefw.validation.field.annotation.NotNull;
import org.springframework.data.util.Pair;

import java.util.Objects;

public class NotNullValidator extends AbstractFieldValidator<NotNull> {
    @Override
    public Pair<Boolean, String> validate(NotNull notNull, Object value) {
        if (Objects.isNull(value)) {
            return Pair.of(Boolean.FALSE, NOT_NULL);
        }
        return Pair.of(Boolean.TRUE, null);
    }

}
