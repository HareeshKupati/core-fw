package com.hbk.corefw.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.hbk.corefw.databind.ObjectMapper;
import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.ErrorDTO;
import com.hbk.corefw.dto.FieldLocation;
import com.hbk.corefw.exception.ResourceNotFoundException;
import com.hbk.corefw.exception.ValidationException;
import com.hbk.corefw.jdo.CoreJDO;
import com.hbk.corefw.repository.CoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.*;
import java.util.stream.Collectors;

public abstract class CoreService<DTO extends CoreDTO, JDO extends CoreJDO, ID, RP extends CoreRepository<JDO, ID>> implements
        ICoreService<DTO, JDO, ID, RP> {

    @Autowired
    private RP repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapperFX;

    public DTO get(ID id) throws ResourceNotFoundException {
        Optional<DTO> optionalUser = repository.findById(id).map(this::mapToDTO);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new ResourceNotFoundException("id", String.valueOf(id), FieldLocation.DATABASE);
    }

    public List<DTO> getAll(Pageable pageable, MultiValueMap<String, String> multiValueMap) {
        return repository.findAll(pageable).stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public DTO create(DTO dto) throws ValidationException {
        JDO jdo = validateAndMapToJDO(dto);
        return mapToDTO(repository.save(jdo));
    }

    public void delete(ID id) throws ResourceNotFoundException {
        Optional<JDO> optionalJDO = repository.findById(id);
        if (optionalJDO.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new ResourceNotFoundException("id", String.valueOf(id), FieldLocation.DATABASE);
        }
    }

    public DTO update(ID id, Map<String, Object> req) throws ResourceNotFoundException, ValidationException {
        DTO dto = get(id);
        HashMap objectMap = objectMapperFX.convertValue(dto, new TypeReference<HashMap<String, Object>>() {
        });
        objectMap.putAll(req);
        DTO updatedDTO = (DTO) objectMapperFX.convertValue(objectMap, dto.getClass());
        JDO jdo = validateAndMapToJDO(updatedDTO);
        return mapToDTO(repository.save(jdo));
    }

    public JDO validateAndMapToJDO(DTO dto) throws ValidationException {
        List<ErrorDTO> errors = new ArrayList<>();
        JDO jdo = objectMapper.convertToJDO(dto, errors);
        validateAndMapToJDO(dto, jdo, errors);
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
        return jdo;
    }

    public abstract void validateAndMapToJDO(DTO dto, JDO jdo, List<ErrorDTO> errors);

    public DTO mapToDTO(JDO jdo) {
        DTO dto = objectMapper.convertToDTO(jdo);
        mapToDTO(jdo, dto);
        return dto;
    }

    public abstract void mapToDTO(JDO jdo,DTO dto);

}
