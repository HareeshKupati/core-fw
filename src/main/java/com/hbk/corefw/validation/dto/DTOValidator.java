package com.hbk.corefw.validation.dto;

import com.hbk.corefw.databind.AbstractObjectMapper;
import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.Error;
import com.hbk.corefw.dto.FieldLocation;
import com.hbk.corefw.util.CoreConstants;
import com.hbk.corefw.validation.field.annotation.Validate;
import com.hbk.corefw.validation.field.validator.IFieldValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.util.Pair;
import org.springframework.util.CollectionUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DTOValidator extends AbstractObjectMapper {

    private Logger logger = LoggerFactory.getLogger(DTOValidator.class);

    private static final Map<Class<? extends IFieldValidator>, IFieldValidator> fieldValidatorsMap = new HashMap<>();

    public <DTO extends CoreDTO> List<Error> validateDTO(DTO dtoObject) {
        return validateDTO(dtoObject, true, null, null);
    }

    private <DTO extends CoreDTO> List<Error> validateDTO(DTO dtoObject, boolean validateInnerDTO, Field parentField, Integer index) {
        List<Error> errors = new ArrayList<>();
        Class dtoClass = dtoObject.getClass();
        while (!Object.class.equals(dtoClass)) {
            for (Field dtoField : dtoClass.getDeclaredFields()) {
                try {
                    Object value = dtoClass.getMethod(getGetterMethodName(dtoField.getName())).invoke(dtoObject);
                    if (!isInnerDTOCollection(dtoField) && !isInnerDTO(dtoField)) {
                        validateDTOField(errors, dtoField, value, parentField, index);
                    } else if (isInnerDTO(dtoField) && validateInnerDTO) {
                        if (value != null) {
                            validateDTO((CoreDTO) value, false, dtoField, null);
                        } else {
                            validateDTOField(errors, dtoField, null, parentField, index);
                        }
                    } else if (isInnerDTOCollection(dtoField) && validateInnerDTO) {
                        List<? extends CoreDTO> childDTOs = (List<? extends CoreDTO>) value;
                        if (!CollectionUtils.isEmpty(childDTOs)) {
                            childDTOs.stream().forEach(childDTO -> validateDTO(childDTO, false, dtoField, childDTOs.indexOf(childDTO)));
                        } else {
                            validateDTOField(errors, dtoField, value, parentField, index);
                        }
                    }
                } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    logger.error("Error while invoking getter method for field {0} of class{1}", dtoField.getName(), dtoClass.getName());
                }
            }
            dtoClass = dtoClass.getSuperclass();
        }
        return errors;
    }

    private void validateDTOField(List<Error> errors, Field dtoField, Object value, Field parentField, Integer index) {
        Error error = validateDTOField(dtoField, value, parentField, index);
        if (error != null)
            errors.add(error);
    }

    private Error validateDTOField(Field srcField, Object value, Field parentField, Integer index) {
        String fieldName = getFieldName(srcField, parentField, index);
        for (Annotation annotation : srcField.getAnnotations()) {
            Validate validate = annotation.annotationType().getAnnotation(Validate.class);
            if (validate != null) {
                IFieldValidator iFieldValidator = getFieldValidator(validate.by());
                Pair<Boolean, String> success = iFieldValidator.validate(annotation, value);
                if (Boolean.FALSE.equals(success.getFirst())) {
                    return buildError(fieldName, String.valueOf(value), success.getSecond());
                }
            }

        }
        return null;
    }

    private Error buildError(String fieldName, String value, String message) {
        return new Error(CoreConstants.BAD_REQUEST, String.format(message, fieldName), fieldName, value, FieldLocation.BODY);
    }

    private String getFieldName(Field srcField, Field parentField, Integer index) {
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

    private IFieldValidator getFieldValidator(Class<? extends IFieldValidator> fieldValidatorClass) {
        IFieldValidator iFieldValidator = fieldValidatorsMap.get(fieldValidatorClass);
        if (iFieldValidator == null) {
            try {
                iFieldValidator = fieldValidatorClass.newInstance();
                fieldValidatorsMap.put(fieldValidatorClass, iFieldValidator);
            } catch (IllegalAccessException | InstantiationException e) {
                logger.error("Unable to create instance of validator class {0}", fieldValidatorClass.getName());
            }
        }
        return iFieldValidator;
    }
}
