package com.hbk.corefw.controller;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.dto.DTOWrapper;
import com.hbk.corefw.exception.ResourceNotFoundException;
import com.hbk.corefw.exception.ValidationException;
import com.hbk.corefw.jdo.CoreJDO;
import com.hbk.corefw.repository.CoreRepository;
import com.hbk.corefw.service.ICoreService;
import com.hbk.corefw.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.hbk.corefw.util.CoreConstants.*;

public class AuthCrudController<DTO extends CoreDTO, JDO extends CoreJDO, ID, RP extends CoreRepository<JDO, ID>, S extends ICoreService<DTO, JDO, ID, RP>> {

    public static final String HAS_ANY_AUTHORITY_ADMIN_OR_RESOURCE_READ_AUTHORITY = "hasAnyAuthority('admin',@coreAuthorityBuilder.readAuthority)";
    public static final String HAS_ANY_AUTHORITY_ADMIN_OR_RESOURCE_WRITE_AUTHORITY = "hasAnyAuthority('admin',@coreAuthorityBuilder.writeAuthority)";
    @Autowired
    private S service;

    @PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_OR_RESOURCE_READ_AUTHORITY)
    @GetMapping
    public ResponseEntity<DTOWrapper<List<DTO>>> getAll(
            @RequestParam MultiValueMap<String, String> allRequestParams,
            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) Integer size,
            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) Integer page,
            @RequestParam(value = SORT, required = false, defaultValue = DEFAULT_SORT) List<String> sort) {
        return ResponseEntity.ok(new DTOWrapper<>(service.getAll(ControllerUtils.getPageable(size, page, sort), allRequestParams)));
    }

    @PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_OR_RESOURCE_READ_AUTHORITY)
    @GetMapping(ID_PATH_PARAM)
    public ResponseEntity<DTOWrapper<DTO>> getById(@PathVariable ID id) throws ResourceNotFoundException {
        return ResponseEntity.ok(new DTOWrapper<>(service.get(id)));
    }

    @PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_OR_RESOURCE_WRITE_AUTHORITY)
    @PostMapping
    public ResponseEntity<DTOWrapper<DTO>> create(@RequestBody DTO req) throws ValidationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(new DTOWrapper<>(service.create(req)));
    }

    @PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_OR_RESOURCE_WRITE_AUTHORITY)
    @PatchMapping(ID_PATH_PARAM)
    public ResponseEntity<DTOWrapper<DTO>> update(@PathVariable ID id, @RequestBody Map<String, Object> req) throws ResourceNotFoundException, ValidationException {
        return ResponseEntity.ok(new DTOWrapper<>(service.update(id, req)));
    }

    @PreAuthorize(HAS_ANY_AUTHORITY_ADMIN_OR_RESOURCE_WRITE_AUTHORITY)
    @DeleteMapping(ID_PATH_PARAM)
    public void delete(@PathVariable ID id) throws ResourceNotFoundException {
        service.delete(id);
    }

}
