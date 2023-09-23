package com.hbk.corefw.test.service;

import com.hbk.corefw.dto.ErrorDTO;
import com.hbk.corefw.service.CoreService;
import com.hbk.corefw.test.dto.RoleDTO;
import com.hbk.corefw.test.jdo.RoleJDO;
import com.hbk.corefw.test.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("roleTestService")
public class RoleService extends CoreService<RoleDTO, RoleJDO, Long, RoleRepository> {

    @Override
    public void validateAndMapToJDO(RoleDTO dto, RoleJDO jdo, List<ErrorDTO> errors) {

    }

    @Override
    public void mapToDTO(RoleJDO jdo, RoleDTO dto) {

    }
}
