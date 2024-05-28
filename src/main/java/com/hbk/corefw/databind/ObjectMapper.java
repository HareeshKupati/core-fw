package com.hbk.corefw.databind;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.jdo.CoreJDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class ObjectMapper extends AbstractObjectMapper {

    private Logger logger = LoggerFactory.getLogger(ObjectMapper.class);

    public <JDO extends CoreJDO, DTO extends CoreDTO> JDO convertToJDO(DTO dto) {
        JDO jdo = getJdoByDTO(dto);
        return (JDO) copy(dto, jdo);
    }

    public <JDO extends CoreJDO, DTO extends CoreDTO> DTO convertToDTO(JDO jdo) {
        DTO dto = getDtoByJDO(jdo);
        return (DTO) copy(jdo, dto);
    }

    private Object copy(Object srcObject, Object destObject) {
        Class srcClass = srcObject.getClass();
        Class destClass = destObject.getClass();
        while (!Object.class.equals(srcClass)) {
            for (Field srcField : srcClass.getDeclaredFields()) {
                try {
                    Field destField = destClass.getDeclaredField(getDestFieldName(srcField.getName()));
                    Object srcValue = srcClass.getMethod(getGetterMethodName(srcField.getName())).invoke(srcObject);
                    if (destField.getType().equals(srcField.getType()) && !isInnerCollection(srcField)) {
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, srcValue);
                    } else if (isInnerObject(srcField, destField) && srcValue != null) {
                        Object destValue = null;
                        if (CoreJDO.class.isAssignableFrom(srcValue.getClass()))
                            destValue = convertToChildDTO((CoreJDO) srcValue);

                        if (CoreDTO.class.isAssignableFrom(srcValue.getClass()))
                            destValue = convertToChildJDO((CoreDTO) srcValue);

                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, destValue);
                    } else if (isInnerCollection(srcField) && srcValue != null) {
                        Type[] actualTypes = ((ParameterizedTypeImpl) srcField.getGenericType()).getActualTypeArguments();
                        if (CoreJDO.class.isAssignableFrom((Class) actualTypes[0])) {
                            List<? extends CoreJDO> coreJDOS = (List<? extends CoreJDO>) srcValue;
                            List<CoreDTO> coreDTOS = coreJDOS.stream().map(JDO -> (CoreDTO) convertToChildDTO((CoreJDO) JDO)).collect(Collectors.toList());
                            destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, coreDTOS);
                        }

                        if (CoreDTO.class.isAssignableFrom((Class) actualTypes[0])) {
                            List<? extends CoreDTO> coreDTOS = (List<? extends CoreDTO>) srcValue;
                            List<CoreJDO> coreJDOS = coreDTOS.stream().map(DTO -> (CoreJDO) convertToChildJDO((CoreDTO) DTO)).collect(Collectors.toList());
                            destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, coreJDOS);
                        }
                    }
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException  e) {
                    logger.error("Error while copying value from {0} of {1} class to {2} class.", srcField.getName(), srcClass, destClass);
                }
            }
            srcClass = srcClass.getSuperclass();
            destClass = destClass.getSuperclass();
        }
        return destObject;
    }

    private <JDO extends CoreJDO, DTO extends CoreDTO> JDO convertToChildJDO(DTO dto) {
        JDO jdo = getJdoByDTO(dto);
        return (JDO) copyChild(dto, jdo);
    }

    private <JDO extends CoreJDO, DTO extends CoreDTO> DTO convertToChildDTO(JDO jdo) {
        DTO dto = getDtoByJDO(jdo);
        return (DTO) copyChild(jdo, dto);
    }

    private Object copyChild(Object srcObject, Object destObject) {
        Class srcClass = srcObject.getClass();
        Class destClass = destObject.getClass();
        while (!Object.class.equals(srcClass)) {
            for (Field srcField : srcClass.getDeclaredFields()) {
                try {
                    Field destField = destClass.getDeclaredField(srcField.getName());
                    if (destField.getType().equals(srcField.getType()) && !isInnerCollection(srcField)) {
                        Object value = srcClass.getMethod(getGetterMethodName(srcField.getName())).invoke(srcObject);
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, value);
                    }
                } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                    logger.error("Error while copying value from {0} of {1} class to {2} class.", srcField.getName(), srcClass, destClass);
                }
            }
            srcClass = srcClass.getSuperclass();
            destClass = destClass.getSuperclass();
        }
        return destObject;
    }

}
