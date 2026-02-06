package com.hbk.corefw.service;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.Error;
import com.hbk.corefw.exception.ResourceNotFoundException;
import com.hbk.corefw.exception.ValidationException;
import com.hbk.corefw.jdo.CoreJDO;
import com.hbk.corefw.repository.CoreRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;

public interface ICoreService<DTO extends CoreDTO, JDO extends CoreJDO, ID, RP extends CoreRepository<JDO, ID>> {
    public DTO get(ID id) throws ResourceNotFoundException;

    public List<DTO> getAll(Pageable pageable, MultiValueMap<String, String> multiValueMap);

    public DTO create(DTO dto) throws ValidationException;

    public void delete(ID id) throws ResourceNotFoundException;

    public DTO update(ID id, Map<String, Object> req) throws ResourceNotFoundException, ValidationException;

    public void mapToDTO(JDO jdo,DTO dto);

    public void mapToJDO(DTO dto, JDO jdo);

    public List<Error> validateDTO(DTO dto);

}
