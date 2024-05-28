package com.hbk.corefw.test.service;

import com.hbk.corefw.dto.Error;
import com.hbk.corefw.service.CoreService;
import com.hbk.corefw.test.dto.UserDTO;
import com.hbk.corefw.test.jdo.UserJDO;
import com.hbk.corefw.test.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userTestService")
public class UserService extends CoreService<UserDTO, UserJDO, Long, UserRepository> {


    @Override
    public void mapToDTO(UserJDO jdo, UserDTO dto) {

    }

    @Override
    public void mapToJDO(UserDTO dto, UserJDO jdo) {

    }

    @Override
    public List<Error> validateDTO(UserDTO dto) {
        return null;
    }
}
