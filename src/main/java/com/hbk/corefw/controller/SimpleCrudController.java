package com.hbk.corefw.controller;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.exception.ResourceNotFoundException;
import com.hbk.corefw.exception.ValidationException;
import com.hbk.corefw.jdo.CoreJDO;
import com.hbk.corefw.repository.CoreRepository;
import com.hbk.corefw.service.ICoreService;
import com.hbk.corefw.util.ControllerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.hbk.corefw.util.CoreConstants.*;

public class SimpleCrudController<DTO extends CoreDTO, JDO extends CoreJDO, ID, RP extends CoreRepository<JDO, ID>, S extends ICoreService<DTO, JDO, ID, RP>> {

    @Autowired
    private S service;

    @GetMapping
    public ResponseEntity<Page<DTO>> getAll(@RequestParam MultiValueMap<String, String> allRequestParams,
                                            @RequestParam(value = SIZE, required = false, defaultValue = DEFAULT_SIZE) Integer size,
                                            @RequestParam(value = PAGE, required = false, defaultValue = DEFAULT_PAGE) Integer page,
                                            @RequestParam(value = SORT, required = false, defaultValue = DEFAULT_SORT) List<String> sort) {
        return ResponseEntity.ok(service.getAll(ControllerUtils.getPageable(size, page, sort), allRequestParams));
    }

    @GetMapping(ID_PATH_PARAM)
    public ResponseEntity<DTO> getById(@PathVariable ID id) throws ResourceNotFoundException {
        return ResponseEntity.ok(service.get(id));
    }

    @PostMapping
    public ResponseEntity<DTO> create(@RequestBody DTO req) throws ValidationException {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PatchMapping(ID_PATH_PARAM)
    public ResponseEntity<DTO> update(@PathVariable ID id, @RequestBody DTO dto) throws ResourceNotFoundException, ValidationException {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping(ID_PATH_PARAM)
    public void delete(@PathVariable ID id) throws ResourceNotFoundException {
        service.delete(id);
    }

}
