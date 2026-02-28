package com.hbk.corefw.databind;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.CoreIdDTO;
import com.hbk.corefw.jdo.CoreIdJDO;
import com.hbk.corefw.jdo.CoreJDO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Validated
public class JdoDtoMapper extends AbstractObjectMapper implements ObjectMapper {

    private final Logger logger = LoggerFactory.getLogger(JdoDtoMapper.class);

    @Override
    public <JDO extends CoreJDO, DTO extends CoreDTO> JDO validateAndConvertToJDO(DTO dto) {
        return convertToJDO(dto);
    }

    @Override
    public <DTO extends CoreDTO> void copyNonNullFields(DTO from, DTO to) {
        copyNonNull(from, to);
    }

    public <JDO extends CoreJDO, DTO extends CoreDTO> JDO convertToJDO(DTO dto) {
        JDO jdo = getJdoByDTO(dto);
        copy(dto, jdo);
        return jdo;
    }

    public <JDO extends CoreJDO, DTO extends CoreDTO> DTO convertToDTO(JDO jdo) {
        DTO dto = getDtoByJDO(jdo);
        copy(jdo, dto);
        return dto;
    }

    private void copy(Object srcObject, Object destObject) {
        copy(srcObject, destObject, null, null);
    }

    private void copy(Object srcObject, Object destObject, Class srcClass, Class destClass) {
        srcClass = srcClass != null ? srcClass : srcObject.getClass();
        destClass = destClass != null ? destClass : destObject.getClass();
        for (Field srcField : srcClass.getDeclaredFields()) {
            try {
                Field destField = destClass.getDeclaredField(getDestFieldName(srcField.getName()));
                Object srcValue = srcClass.getMethod(getGetterMethodName(srcField.getName())).invoke(srcObject);
                if (destField.getType().equals(srcField.getType()) && !isInnerCollection(srcField)) {
                    destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, srcValue);
                } else if (isInnerObject(srcField, destField) && srcValue != null) {
                    Object destValue = null;
                    if (CoreJDO.class.isAssignableFrom(srcValue.getClass())) {
                        destValue = convertToDTO((CoreJDO) srcValue);
                    } else if (CoreDTO.class.isAssignableFrom(srcValue.getClass())) {
                        destValue = convertToJDO((CoreDTO) srcValue);
                    }
                    destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, destValue);
                } else if (isInnerCollection(srcField) && srcValue != null) {
                    Type[] actualTypes = ((ParameterizedType) srcField.getGenericType()).getActualTypeArguments();
                    if (CoreJDO.class.isAssignableFrom((Class) actualTypes[0])) {
                        List<? extends CoreJDO> coreJDOS = (List<? extends CoreJDO>) srcValue;
                        List<CoreDTO> coreDTOS = coreJDOS.stream().map(JDO -> (CoreDTO) convertToDTO((CoreJDO) JDO)).collect(Collectors.toList());
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, coreDTOS);
                    }

                    if (CoreDTO.class.isAssignableFrom((Class) actualTypes[0])) {
                        List<? extends CoreDTO> coreDTOS = (List<? extends CoreDTO>) srcValue;
                        List<CoreJDO> coreJDOS = coreDTOS.stream().map(DTO -> (CoreJDO) convertToJDO((CoreDTO) DTO)).collect(Collectors.toList());
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, coreJDOS);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                logger.error("Error while copying value from {} of {} class to {} class.", srcField.getName(), srcClass, destClass);
            }
        }
        if (!Object.class.equals(srcClass.getSuperclass())) {
            copy(srcObject, destObject, srcClass.getSuperclass(), destClass.getSuperclass());
        }
    }

    private void copyNonNull(Object srcObject, Object destObject) {
        copyNonNull(srcObject, destObject, null, null);
    }

    private void copyNonNull(Object srcObject, Object destObject, Class srcClass, Class destClass) {
        srcClass = srcClass != null ? srcClass : srcObject.getClass();
        destClass = destClass != null ? destClass : destObject.getClass();
        for (Field srcField : srcClass.getDeclaredFields()) {
            try {
                Field destField = destClass.getDeclaredField(srcField.getName());
                Object srcValue = srcClass.getMethod(getGetterMethodName(srcField.getName())).invoke(srcObject);
                Object destValue = destClass.getMethod(getGetterMethodName(destField.getName())).invoke(destObject);
                if (destField.getType().equals(srcField.getType()) && !isInnerCollection(srcField) && srcValue != null) {
                    destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, srcValue);
                } else if (isInnerObject(srcField, destField) && srcValue != null) {
                    if (destValue != null) {
                        copyNonNull(srcObject, destObject);
                    } else {
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, srcValue);
                    }
                } else if (isInnerCollection(srcField) && srcValue != null) {
                    if (destValue != null) {
                        List srcValueList = (List) srcValue;
                        List destValueList = (List) destValue;
                        for (Object src : srcValueList) {
                            Long srcId = getId(src);
                            boolean matched = false;
                            for (Object dest : destValueList) {
                                Long destId = getId(dest);
                                if (srcId != null && srcId.equals(destId)) {
                                    copyNonNull(src, dest);
                                    matched = true;
                                }
                            }
                            if (!matched) destValueList.add(src);
                        }
                    } else {
                        destClass.getMethod(getSetterMethodName(destField.getName()), destField.getType()).invoke(destObject, srcValue);
                    }
                }
            } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                logger.error("Error while copying value from {} of {} class to {} class.", srcField.getName(), srcClass, destClass);
            }
        }
        if (!Object.class.equals(srcClass.getSuperclass())) {
            copy(srcObject, destObject, srcClass.getSuperclass(), destClass.getSuperclass());
        }
    }

    private Long getId(Object object) {
        if (object instanceof CoreIdDTO dto) return dto.getId();

        if (object instanceof CoreIdJDO jdo) return jdo.getId();

        return null;
    }

}
