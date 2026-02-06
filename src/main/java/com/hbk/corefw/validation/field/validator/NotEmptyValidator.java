package com.hbk.corefw.validation.field.validator;

import com.hbk.corefw.validation.field.annotation.NotEmpty;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;

@Component
public class NotEmptyValidator extends AbstractFieldValidator<NotEmpty> {
    @Override
    public Pair<Boolean, String> validate(NotEmpty notEmpty, Object value) {
        if (Objects.isNull(value)) {
            return Pair.of(Boolean.FALSE, NOT_NULL);
        } else if ((value instanceof String && ((String) value).isEmpty())
                || (value instanceof Collection && ((Collection) value).isEmpty())
                || (value instanceof Map && ((Map) value).isEmpty())) {
            return Pair.of(Boolean.FALSE, NOT_EMPTY);
        } else if (value instanceof String &&
                (((String) value).length() < notEmpty.minLength() || (((String) value).length() > notEmpty.maxLength()))) {
            return Pair.of(Boolean.FALSE,
                    LENGTH.replace("min", String.valueOf(notEmpty.minLength())).replace("max", String.valueOf(notEmpty.maxLength())));
        }
        return Pair.of(Boolean.TRUE, "");
    }
}
