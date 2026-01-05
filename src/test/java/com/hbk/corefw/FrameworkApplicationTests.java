package com.hbk.corefw;

import com.hbk.corefw.databind.ObjectMapper;
import com.hbk.corefw.dto.Error;
import com.hbk.corefw.exception.ResourceNotFoundException;
import com.hbk.corefw.test.dto.ChildDTO;
import com.hbk.corefw.test.dto.ParentDTO;
import com.hbk.corefw.test.dto.SuperParentDTO;
import com.hbk.corefw.test.jdo.ChildJDO;
import com.hbk.corefw.test.jdo.ParentJDO;
import com.hbk.corefw.test.jdo.SuperParentJDO;
import com.hbk.corefw.test.service.RoleService;
import com.hbk.corefw.test.service.UserService;
import com.hbk.corefw.validation.dto.DTOValidator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest
class FrameworkApplicationTests {
    
    Logger logger = LoggerFactory.getLogger(FrameworkApplicationTests.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    private ObjectMapper objectMapper = new ObjectMapper();
    private DTOValidator dtoValidator = new DTOValidator();

    @Autowired
    private com.fasterxml.jackson.databind.ObjectMapper objectMapperDB;

    @Test
    void contextLoads() {
    }

    @Test
    void testCoreService() throws ResourceNotFoundException {
//		logger.info("****************************** Core Service Test Started ***********************************");
//		UserDTO savedUser = userService.create(new UserDTO(36l,"Hareesh","Kupate","HareeshKupate36", "2@g11EC006"));
//		RoleDTO savedRole = roleService.create(new RoleDTO(36l,"Admin36","I am Admin"));
//		UserDTO loadedUser = userService.get(savedUser.getId());
//		RoleDTO loadedRole = roleService.get(savedRole.getId());
//		Assertions.assertEquals(savedUser, loadedUser);
//		Assertions.assertEquals(savedRole, loadedRole);
//		Map<String, Object> userMap = new HashMap<>();
//		userMap.put("firstName", "Hari");
//		UserDTO updatedUser = userService.update(savedUser.getId(), userMap);


//		Map<String, Object> roleMap = new HashMap<>();
//		roleMap.put("roleName", "IamAdmin111");
//		RoleDTO updatedRole = roleService.update(savedRole.getId(), roleMap);
//
//		UserDTO loadUpdatedUser = userService.get(savedUser.getId());
//		RoleDTO loadUpdatedRole = roleService.get(savedRole.getId());
//		Assertions.assertEquals(loadUpdatedUser.getFirstName(), "Hari");
//		userService.delete(savedUser.getId());
//		roleService.delete(savedRole.getId());
//		Assertions.assertThrows(ResourceNotFoundException.class, () -> userService.get(savedUser.getId()));
//		Assertions.assertEquals(loadUpdatedRole.getRoleName(), "IamAdmin111");
//		Assertions.assertEquals(2, userService.getAll(Pageable.ofSize(2)).size());
//		logger.info("****************************** Core Service Test Ended ***********************************");
    }

    @Test
    void testObjectMapper() throws IOException {
        logger.info("*** JDO Mapping test started ***");
        FileReader fileReader = new FileReader("./src/test/resources/ParentDTO.txt");
        ParentDTO parentDTO1 = objectMapperDB.readValue(fileReader, ParentDTO.class);
        fileReader.close();
        ParentJDO parentJDO1 = objectMapper.convertToJDO(parentDTO1);
        assertParentDTOAndJDO(parentDTO1, parentJDO1);
        logger.info("*** JDO Mapping test ended ***");

        logger.info("*** DTO Mapping test started ***");
        fileReader = new FileReader("./src/test/resources/ParentJDO.txt");
        ParentJDO parentJDO2 = objectMapperDB.readValue(fileReader, ParentJDO.class);
        fileReader.close();
        ParentDTO parentDTO2 = objectMapper.convertToDTO(parentJDO2);
        assertParentDTOAndJDO(parentDTO2, parentJDO2);
        logger.info("*** DTO Mapping test ended ***");
        logger.info("*** DTO Validation test started ***");
        List<Error> errors = dtoValidator.validateDTO(parentDTO1);
        logger.info("*** DTO Validation test ended ***");

    }

    private void assertParentDTOAndJDO(ParentDTO parentDTO, ParentJDO parentJDO) {
        Assertions.assertEquals(parentJDO.getId(), parentDTO.getId());
        Assertions.assertEquals(parentJDO.getAmount(), parentDTO.getAmount());
        Assertions.assertEquals(parentJDO.getCount(), parentDTO.getCount());
        Assertions.assertEquals(parentJDO.getIsTest(), parentDTO.getIsTest());
        Assertions.assertEquals(parentJDO.getDate(), parentDTO.getDate());
        Assertions.assertEquals(parentJDO.getPrevId(), parentDTO.getPrevId());
        Assertions.assertEquals(parentJDO.getIndex(), parentDTO.getIndex());
        Assertions.assertEquals(parentJDO.getName(), parentDTO.getName());
        Assertions.assertEquals(parentJDO.getRoles(), parentDTO.getRoles());
        if(parentJDO.getSuperParentJDO() != null){
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getId(), parentDTO.getSuperParentDTO().getId());
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getAmount(), parentDTO.getSuperParentDTO().getAmount());
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getCount(), parentDTO.getSuperParentDTO().getCount());
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getIsTest(), parentDTO.getSuperParentDTO().getIsTest());
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getDate(), parentDTO.getSuperParentDTO().getDate());
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getPrevId(), parentDTO.getSuperParentDTO().getPrevId());
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getIndex(), parentDTO.getSuperParentDTO().getIndex());
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getName(), parentDTO.getSuperParentDTO().getName());
            Assertions.assertEquals(parentJDO.getSuperParentJDO().getRoles(), parentDTO.getSuperParentDTO().getRoles());

        }
        if(parentJDO.getChildJDOs() != null){
            Assertions.assertEquals(parentJDO.getChildJDOs().size(), parentDTO.getChildDTOs().size());
            for(ChildJDO childJDO : parentJDO.getChildJDOs()){
                for(ChildDTO childDTO : parentDTO.getChildDTOs()){
                    if(childDTO.getId().equals(childJDO.getId())){
                        Assertions.assertEquals(childJDO.getId(), childDTO.getId());
                        Assertions.assertEquals(childJDO.getAmount(), childDTO.getAmount());
                        Assertions.assertEquals(childJDO.getCount(), childDTO.getCount());
                        Assertions.assertEquals(childJDO.getIsTest(), childDTO.getIsTest());
                        Assertions.assertEquals(childJDO.getDate(), childDTO.getDate());
                        Assertions.assertEquals(childJDO.getPrevId(), childDTO.getPrevId());
                        Assertions.assertEquals(childJDO.getIndex(), childDTO.getIndex());
                        Assertions.assertEquals(childJDO.getName(), childDTO.getName());
                        Assertions.assertEquals(childJDO.getRoles(), childDTO.getRoles());
                    }
                }
            }
        }
    }

}
