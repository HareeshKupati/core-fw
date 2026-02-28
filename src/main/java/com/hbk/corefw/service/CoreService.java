package com.hbk.corefw.service;

import com.hbk.corefw.databind.ObjectMapper;
import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.Error;
import com.hbk.corefw.dto.FieldLocation;
import com.hbk.corefw.exception.ResourceNotFoundException;
import com.hbk.corefw.exception.ValidationException;
import com.hbk.corefw.jdo.CoreJDO;
import com.hbk.corefw.repository.CoreRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public abstract class CoreService<DTO extends CoreDTO, JDO extends CoreJDO, ID, RP extends CoreRepository<JDO, ID>> implements ICoreService<DTO, JDO, ID, RP> {

    @Autowired
    protected RP repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    Validator validator;

    public DTO get(ID id) {
        return repository.findById(id).map(this::mapToDTO).orElseThrow(() -> new ResourceNotFoundException("id", String.valueOf(id), FieldLocation.DATABASE));
    }

    public Page<DTO> getAll(Pageable pageable, MultiValueMap<String, String> multiValueMap) {
        return repository.findAll(pageable).map(this::mapToDTO);
    }

    public DTO create(DTO dto) {
        validate(dto);
        JDO jdo = mapToJDO(dto);
        jdo = repository.save(jdo);
        return mapToDTO(jdo);
    }

    public void delete(ID id) {
        repository.findById(id).ifPresentOrElse(jdo -> repository.deleteById(id), () -> {
            throw new ResourceNotFoundException("id", String.valueOf(id), FieldLocation.DATABASE);
        });

    }

    public DTO update(ID id, DTO dto) {
        DTO dto2 = get(id);
        objectMapper.copyNonNullFields(dto, dto2);
        JDO jdo = mapToJDO(dto2);
        return mapToDTO(repository.save(jdo));
    }

    public void validate(DTO dto) {
        List<Error> errors = new ArrayList<>();
        Set<ConstraintViolation<DTO>> constraintViolations = validator.validate(dto);
        errors.addAll(constraintViolations.stream().map(cv -> new Error(cv.getMessage(), String.valueOf(cv.getPropertyPath()), String.valueOf(cv.getInvalidValue()), FieldLocation.BODY)).toList());
        errors.addAll(validateDTO(dto));
        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public JDO mapToJDO(DTO dto) {
        JDO jdo = objectMapper.validateAndConvertToJDO(dto);
        mapToJDO(dto, jdo);
        return jdo;
    }

    public DTO mapToDTO(JDO jdo) {
        DTO dto = objectMapper.convertToDTO(jdo);
        mapToDTO(jdo, dto);
        return dto;
    }

    public abstract void mapToDTO(JDO jdo, DTO dto);

    public abstract void mapToJDO(DTO dto, JDO jdo);

    public abstract List<Error> validateDTO(DTO dto);

}
