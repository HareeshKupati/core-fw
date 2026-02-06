package com.hbk.corefw.databind;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.jdo.CoreJDO;

public interface ObjectMapper {

    <JDO extends CoreJDO, DTO extends CoreDTO> JDO convertToJDO(DTO dto);

    <JDO extends CoreJDO, DTO extends CoreDTO> DTO convertToDTO(JDO jdo);

}
