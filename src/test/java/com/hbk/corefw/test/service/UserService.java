package com.hbk.corefw.test.service;

import com.hbk.corefw.dto.ErrorDTO;
import com.hbk.corefw.service.CoreService;
import com.hbk.corefw.test.dto.UserDTO;
import com.hbk.corefw.test.jdo.UserJDO;
import com.hbk.corefw.test.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userTestService")
public class UserService extends CoreService<UserDTO, UserJDO, Long, UserRepository> {


    @Override
    public void validateAndMapToJDO(UserDTO dto, UserJDO jdo, List<ErrorDTO> errors) {

    }

    @Override
    public void mapToDTO(UserJDO jdo, UserDTO dto) {

    }
}
