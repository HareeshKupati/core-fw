package com.hbk.corefw.validation.field.validator;

import com.hbk.corefw.validation.field.annotation.NotEmpty;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Component
public class NotEmptyValidator extends AbstractFieldValidator<NotEmpty> {
    @Override
    public Pair<Boolean, String> validate(NotEmpty notEmpty, Object value) {
        if (Objects.isNull(value)) {
            return new Pair<>(Boolean.FALSE, NOT_NULL);
        } else if ((value instanceof String && ((String) value).isEmpty())
                || (value instanceof Collection && ((Collection) value).isEmpty())
                || (value instanceof Map && ((Map) value).isEmpty())) {
            return new Pair<>(Boolean.FALSE, NOT_EMPTY);
        } else if (value instanceof String &&
                (((String) value).length() < notEmpty.minLength() || (((String) value).length() > notEmpty.maxLength()))) {
            new Pair<>(Boolean.FALSE,
                    LENGTH.replace("min", String.valueOf(notEmpty.minLength())).replace("max", String.valueOf(notEmpty.maxLength())));
        }
        return new Pair<>(Boolean.TRUE, null);
    }
}
