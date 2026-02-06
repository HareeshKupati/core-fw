package com.hbk.corefw.databind;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.jdo.CoreJDO;
import com.hbk.corefw.util.CoreConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Optional;

public abstract class AbstractObjectMapper {

    private Logger logger = LoggerFactory.getLogger(AbstractObjectMapper.class);

    public static final String GET = "get";
    public static final String SET = "set";

    protected String getDestFieldName(String srcFieldName) {
        if (srcFieldName.contains("DTO")) {
            return srcFieldName.replace("DTO", "JDO");
        } else if (srcFieldName.contains("JDO")) {
            return srcFieldName.replace("JDO", "DTO");
        }
        return srcFieldName;
    }

    protected boolean isInnerObject(Field srcField, Field destField) {
        if (CoreJDO.class.isAssignableFrom(srcField.getType())) {
            return destField.getType().equals(getDTOClass((Class<? extends CoreJDO>) srcField.getType()).get());
        }
        if (CoreDTO.class.isAssignableFrom(srcField.getType())) {
            return destField.getType().equals(getJDOClass((Class<? extends CoreDTO>) srcField.getType()).get());
        }
        return false;
    }

    protected boolean isInnerCollection(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            Type actualType = ((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0];
            return CoreDTO.class.isAssignableFrom((Class) actualType) || CoreJDO.class.isAssignableFrom((Class) actualType);
        }
        return false;
    }

    protected <JDO extends CoreJDO, DTO extends CoreDTO> JDO getJdoByDTO(DTO dto) {
        Class<? extends CoreJDO> jdoClass = null;
        try {
            jdoClass = getJDOClass(dto.getClass()).orElseThrow(InstantiationException::new);
            return (JDO) jdoClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("Error while creating object of {0} class.", jdoClass);
        }
        return null;
    }

    protected <JDO extends CoreJDO, DTO extends CoreDTO> DTO getDtoByJDO(JDO jdo) {
        Class<? extends CoreDTO> dtoClass = null;
        try {
            dtoClass = getDTOClass(jdo.getClass()).orElseThrow(InstantiationException::new);
            return (DTO) dtoClass.newInstance();
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("Error while creating object of {0} class.", dtoClass);
        }
        return null;
    }

    protected Optional<Class<? extends CoreDTO>> getDTOClass(Class<? extends CoreJDO> jdoClass) {
        String dtoClassName = jdoClass.getName()
                .replace(CoreConstants.jdo, CoreConstants.dto)      // for package
                .replace(CoreConstants.JDO, CoreConstants.DTO);     // for class name
        try {
            return Optional.ofNullable((Class<? extends CoreDTO>) Class.forName(dtoClassName));
        } catch (ClassNotFoundException e) {
            logger.error("Error while loading DTO class {0} for JDO class {1}", dtoClassName, jdoClass.getName());
        }
        return Optional.empty();
    }

    protected Optional<Class<? extends CoreJDO>> getJDOClass(Class<? extends CoreDTO> dtoClass) {
        String jdoClassName = dtoClass.getName()
                .replace(CoreConstants.dto, CoreConstants.jdo)      // for package
                .replace(CoreConstants.DTO, CoreConstants.JDO);     // for class name
        try {
            return Optional.ofNullable((Class<? extends CoreJDO>) Class.forName(jdoClassName));
        } catch (ClassNotFoundException e) {
            logger.error("Error while loading JDO class {0} for DTO class {1}", jdoClassName, dtoClass.getName());
        }
        return Optional.empty();
    }

    protected String getGetterMethodName(String fieldName) {
        return GET +
                String.valueOf(fieldName.charAt(0)).toUpperCase() +
                (fieldName.length() > 1 ? fieldName.substring(1) : "");
    }

    protected String getSetterMethodName(String fieldName) {
        return SET +
                String.valueOf(fieldName.charAt(0)).toUpperCase() +
                (fieldName.length() > 1 ? fieldName.substring(1) : "");
    }

    protected boolean isInnerDTO(Field field) {
        return CoreDTO.class.isAssignableFrom(field.getType());
    }

    protected boolean isInnerDTOCollection(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            Type[] actualTypes = ((ParameterizedType) field.getGenericType()).getActualTypeArguments();
            return CoreDTO.class.isAssignableFrom((Class) actualTypes[0]);
        }
        return false;
    }

}
