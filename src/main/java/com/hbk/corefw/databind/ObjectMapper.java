package com.hbk.corefw.databind;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.jdo.CoreJDO;
import jakarta.validation.Valid;

public interface ObjectMapper {

    <JDO extends CoreJDO, DTO extends CoreDTO> JDO validateAndConvertToJDO(DTO dto);

    <JDO extends CoreJDO, DTO extends CoreDTO> JDO convertToJDO(DTO dto);

    <JDO extends CoreJDO, DTO extends CoreDTO> DTO convertToDTO(JDO jdo);

    <DTO extends CoreDTO> void copyNonNullFields(DTO from, DTO to);
}
