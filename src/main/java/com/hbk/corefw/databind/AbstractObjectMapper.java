package com.hbk.corefw.databind;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.Error;
import com.hbk.corefw.jdo.CoreJDO;
import com.hbk.corefw.util.CoreConstants;
import com.hbk.corefw.validation.dto.DTOValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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
            return destField.getType().equals(getDTOClass((Class<? extends CoreJDO>) srcField.getType()));
        }
        if (CoreDTO.class.isAssignableFrom(srcField.getType())) {
            return destField.getType().equals(getJDOClass((Class<? extends CoreDTO>) srcField.getType()));
        }
        return false;
    }

    protected boolean isInnerCollection(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            Type[] actualTypes = ((ParameterizedTypeImpl) field.getGenericType()).getActualTypeArguments();
            return CoreDTO.class.isAssignableFrom((Class) actualTypes[0]) || CoreJDO.class.isAssignableFrom((Class) actualTypes[0]);
        }
        return false;
    }

    protected <JDO extends CoreJDO, DTO extends CoreDTO> JDO getJdoByDTO(DTO dto) {
        JDO jdo = null;
        Class<? extends CoreJDO> jdoClass = getJDOClass(dto.getClass());
        if (jdoClass != null) {
            try {
                jdo = (JDO) jdoClass.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                logger.error("Error while creating object of {0} class.", jdoClass);
            }
        }
        return jdo;
    }

    protected <JDO extends CoreJDO, DTO extends CoreDTO> DTO getDtoByJDO(JDO jdo) {
        DTO dto = null;
        Class<? extends CoreDTO> dtoClass = getDTOClass(jdo.getClass());
        if (dtoClass != null) {
            try {
                dto = (DTO) dtoClass.newInstance();
            } catch (IllegalAccessException | InstantiationException e) {
                logger.error("Error while creating object of {0} class.", dtoClass);
            }
        }
        return dto;
    }

    protected Class<? extends CoreDTO> getDTOClass(Class<? extends CoreJDO> jdoClass) {
        String dtoClassName = jdoClass.getName()
                .replace(CoreConstants.jdo, CoreConstants.dto)      // for package
                .replace(CoreConstants.JDO, CoreConstants.DTO);     // for class name
        try {
            return (Class<? extends CoreDTO>) Class.forName(dtoClassName);
        } catch (ClassNotFoundException e) {
            logger.error("Error while loading DTO class {0} for JDO class {1}", dtoClassName, jdoClass.getName());
        }
        return null;
    }

    protected Class<? extends CoreJDO> getJDOClass(Class<? extends CoreDTO> dtoClass) {
        String jdoClassName = dtoClass.getName()
                .replace(CoreConstants.dto, CoreConstants.jdo)      // for package
                .replace(CoreConstants.DTO, CoreConstants.JDO);     // for class name
        try {
            return (Class<? extends CoreJDO>) Class.forName(jdoClassName);
        } catch (ClassNotFoundException e) {
            logger.error("Error while loading JDO class {0} for DTO class {1}", jdoClassName, dtoClass.getName());
        }
        return null;
    }

    protected String getGetterMethodName(String fieldName) {
        String firstCharacter = new Character(fieldName.charAt(0)).toString().toUpperCase();
        return GET + firstCharacter + (fieldName.length() > 1 ? fieldName.substring(1) : "");
    }

    protected String getSetterMethodName(String fieldName) {
        String firstCharacter = new Character(fieldName.charAt(0)).toString().toUpperCase();
        return SET + firstCharacter + (fieldName.length() > 1 ? fieldName.substring(1) : "");
    }

    protected boolean isInnerDTO(Field field) {
        return CoreDTO.class.isAssignableFrom(field.getType());
    }

    protected boolean isInnerDTOCollection(Field field) {
        if (Collection.class.isAssignableFrom(field.getType())) {
            Type[] actualTypes = ((ParameterizedTypeImpl) field.getGenericType()).getActualTypeArguments();
            return CoreDTO.class.isAssignableFrom((Class) actualTypes[0]);
        }
        return false;
    }

}
