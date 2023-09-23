package com.hbk.corefw.util;

import com.hbk.corefw.dto.ErrorDTO;
import com.hbk.corefw.dto.FieldLocation;
import com.hbk.corefw.validation.annotation.NotEmpty;
import com.hbk.corefw.validation.annotation.NotNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ValidateUtils {

    public static ErrorDTO validateDTOField(Field srcField, Object value) {
        return validateDTOField(srcField, value, null, null);
    }

    public static ErrorDTO validateDTOField(Field srcField, Object value, Field parentField, Integer index) {
        String fieldName = getFieldName(srcField, parentField, index);
        for (Annotation annotation : srcField.getAnnotations()) {
            if (NotNull.class.equals(annotation.annotationType())) {
                return ValidateUtils.validateNotNull(fieldName, value);
            } else if (NotEmpty.class.equals(annotation.annotationType())) {
                return ValidateUtils.validateNotEmpty(fieldName, value, ((NotEmpty) annotation).maxLength());
            }
        }
        return null;
    }

    private static String getFieldName(Field srcField, Field parentField, Integer index) {
        StringBuilder fieldNameBuilder = new StringBuilder();
        if (parentField != null) {
            fieldNameBuilder.append(parentField.getName());
            if (index != null) {
                fieldNameBuilder.append("[").append(index).append("]");
            }
            fieldNameBuilder.append(".");
        }
        return fieldNameBuilder.append(srcField.getName()).toString();
    }

    public static ErrorDTO validateNotNull(String fieldName, Object value) {
        if (value == null) {
            return new ErrorDTO(CoreConstants.BAD_REQUEST, String.format(CoreConstants.NOT_NULL, fieldName), fieldName, FieldLocation.BODY);
        }
        return null;
    }

    public static ErrorDTO validateNotEmpty(String fieldName, Object value, int maxLength) {
        ErrorDTO errorDTO = validateNotNull(fieldName, value);
        if (errorDTO != null) {
            return errorDTO;
        } else {
            if (value instanceof String) {
                String sValue = (String) value;
                if (sValue.trim().isEmpty()) {
                    return new ErrorDTO(CoreConstants.BAD_REQUEST, String.format(CoreConstants.NOT_EMPTY, fieldName), fieldName, FieldLocation.BODY);
                } else if (sValue.length() > maxLength) {
                    return new ErrorDTO(CoreConstants.BAD_REQUEST, String.format(CoreConstants.MAX_LENGTH, fieldName, maxLength), fieldName, FieldLocation.BODY);
                }
            }
        }
        return null;
    }
}
