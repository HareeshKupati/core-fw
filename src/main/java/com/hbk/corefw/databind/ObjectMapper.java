package com.hbk.corefw.databind;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.ErrorDTO;
import com.hbk.corefw.jdo.CoreJDO;
import com.hbk.corefw.util.CoreConstants;
import com.hbk.corefw.util.ValidateUtils;
import org.springframework.stereotype.Service;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
public class ObjectMapper {

    public static final String GET = "get";
    public static final String SET = "set";

    public <JDO extends CoreJDO, DTO extends CoreDTO> JDO convertToJDO(DTO dto, List<ErrorDTO> errors) {
        JDO jdo = getJdoByDTO(dto);
        return (JDO) copy(dto, jdo, true, errors);
    }

    public <JDO extends CoreJDO, DTO extends CoreDTO> DTO convertToDTO(JDO jdo) {
        DTO dto = getDtoByJDO(jdo);
        return (DTO) copy(jdo, dto, false, null);
    }

    private Object copy(Object srcObject, Object destObject, boolean validate, List<ErrorDTO> errors) {
        Class srcClass = srcObject.getClass();
        Class destClass = destObject.getClass();
        while(!Object.class.equals(srcClass)){
            for (Field srcField : srcClass.getDeclaredFields()) {
                try {
                    Field destField = destClass.getDeclaredField(srcField.getName());
                    if (destField.getType().equals(srcField.getType()) && !isInnerCollection(srcField)) {
                        Object value = srcClass.getMethod(getGetterMethodName(srcField.getName())).invoke(srcObject);
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, value);
                        if (validate) {
                            ErrorDTO errorDTO = ValidateUtils.validateDTOField(srcField, value);
                            if (errorDTO != null) errors.add(errorDTO);
                        }
                    } else if(isInnerObject(srcField, destField)){

                        Object value = srcClass.getMethod(getGetterMethodName(srcField.getName())).invoke(srcObject);
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, value);
                        if (validate) {
                            ErrorDTO errorDTO = ValidateUtils.validateDTOField(srcField, value);
                            if (errorDTO != null) errors.add(errorDTO);
                        }

                    } else if(isInnerCollection(srcField)){

                    }
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            srcClass = srcClass.getSuperclass();
            destClass = destClass.getSuperclass();
        }
        return destObject;
    }

    private Object copyChild(Object srcObject, Object destObject, boolean validate, List<ErrorDTO> errors, Field parentField, Integer index) {
        Class srcClass = srcObject.getClass();
        Class destClass = destObject.getClass();
        while(!Object.class.equals(srcClass)){
            for (Field srcField : srcClass.getDeclaredFields()) {
                try {
                    Field destField = destClass.getDeclaredField(srcField.getName());
                    if (destField.getType().equals(srcField.getType())) {
                        Object value = srcClass.getMethod(getGetterMethodName(srcField.getName())).invoke(srcObject);
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, value);
                        if (validate) {
                            ErrorDTO errorDTO = ValidateUtils.validateDTOField(srcField, value, parentField, index);
                            if (errorDTO != null) errors.add(errorDTO);
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            srcClass = srcClass.getSuperclass();
            destClass = destClass.getSuperclass();
        }
        return destObject;
    }

    private boolean isInnerObject(Field srcfield) {
        if(){

        }
        return field.getType().isAssignableFrom(CoreJDO.class) || field.getType().isAssignableFrom(CoreDTO.class);
    }

    private boolean isInnerCollection(Field field) {
        if (field.getType().isAssignableFrom(List.class) || field.getType().isAssignableFrom(Set.class)) {
            Type[] actualTypes = ((ParameterizedTypeImpl) field.getGenericType()).getActualTypeArguments();
            return ((Class) actualTypes[0]).isAssignableFrom(CoreDTO.class) || ((Class) actualTypes[0]).isAssignableFrom(CoreJDO.class);
        }
        return false;
    }

    private <JDO extends CoreJDO, DTO extends CoreDTO> JDO getJdoByDTO(DTO dto) {
        String jdoClassName = dto.getClass().getName()
                .replace(CoreConstants.dto, CoreConstants.jdo)      // For package
                .replace(CoreConstants.DTO, CoreConstants.JDO);     // For class name
        JDO jdo = null;
        try {
            jdo = (JDO) Class.forName(jdoClassName).newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return jdo;
    }

    private <JDO extends CoreJDO, DTO extends CoreDTO> DTO getDtoByJDO(JDO jdo) {
        String dtoClassName = jdo.getClass().getName()
                .replace(CoreConstants.jdo, CoreConstants.dto)      // for package
                .replace(CoreConstants.JDO, CoreConstants.DTO);     // for class name
        DTO dto = null;
        try {
            dto = (DTO) Class.forName(dtoClassName).newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return dto;
    }

    private String getGetterMethodName(String fieldName) {
        String firstCharacter = new Character(fieldName.charAt(0)).toString().toUpperCase();
        return GET + firstCharacter + (fieldName.length() > 1 ? fieldName.substring(1) : "");
    }

    private String getSetterMethodName(String fieldName) {
        String firstCharacter = new Character(fieldName.charAt(0)).toString().toUpperCase();
        return SET + firstCharacter + (fieldName.length() > 1 ? fieldName.substring(1) : "");
    }

}
