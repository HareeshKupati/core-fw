package com.hbk.corefw;

import com.hbk.corefw.databind.ObjectMapper;
import com.hbk.corefw.exception.ResourceNotFoundException;
import com.hbk.corefw.test.dto.ParentDTO;
import com.hbk.corefw.test.dto.RoleDTO;
import com.hbk.corefw.test.dto.UserDTO;
import com.hbk.corefw.test.jdo.ParentJDO;
import com.hbk.corefw.test.jdo.UserJDO;
import com.hbk.corefw.test.service.RoleService;
import com.hbk.corefw.test.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.*;

@SpringBootTest
class FrameworkApplicationTests {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void testCoreService() throws ResourceNotFoundException {
//		System.out.println("****************************** Core Service Test Started ***********************************");
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
//		System.out.println("****************************** Core Service Test Ended ***********************************");
	}

	@Test
	void testObjectMapper() {
		System.out.println("****************************** JDO Mapping test started ***********************************");
		ParentDTO parentDTO = new ParentDTO("Hareesh",new BigDecimal(100),new Date(), 2000,false, 1000l, 11, Arrays.asList("Hari"));
		parentDTO.setId(1l);
		ParentJDO parentJDO = objectMapper.convertToJDO(parentDTO, new ArrayList<>());
		Assertions.assertEquals(parentJDO.getId(), parentDTO.getId());
		Assertions.assertEquals(parentJDO.getAmount(), parentDTO.getAmount());
		Assertions.assertEquals(parentJDO.getCount(), parentDTO.getCount());
		Assertions.assertEquals(parentJDO.getIsTest(), parentDTO.getIsTest());
		Assertions.assertEquals(parentJDO.getDate(), parentDTO.getDate());
		Assertions.assertEquals(parentJDO.getPrevId(), parentDTO.getPrevId());
		Assertions.assertEquals(parentJDO.getIndex(), parentDTO.getIndex());
		Assertions.assertEquals(parentJDO.getName(), parentDTO.getName());
		Assertions.assertEquals(parentJDO.getRoles(), parentDTO.getRoles());

		ParentJDO parentJDO1 = new ParentJDO("Hareesh",new BigDecimal(100),new Date(), 2000,false, 1000l, 11, Arrays.asList("Sowmi") );
		parentJDO1.setId(1l);
		ParentDTO parentDTO1 = objectMapper.convertToDTO(parentJDO1);
		Assertions.assertEquals(parentJDO1.getId(), parentDTO1.getId());
		Assertions.assertEquals(parentJDO1.getAmount(), parentDTO1.getAmount());
		Assertions.assertEquals(parentJDO1.getCount(), parentDTO1.getCount());
		Assertions.assertEquals(parentJDO1.getIsTest(), parentDTO1.getIsTest());
		Assertions.assertEquals(parentJDO1.getDate(), parentDTO1.getDate());
		Assertions.assertEquals(parentJDO1.getPrevId(), parentDTO1.getPrevId());
		Assertions.assertEquals(parentJDO1.getIndex(), parentDTO1.getIndex());
		Assertions.assertEquals(parentJDO1.getName(), parentDTO1.getName());
		Assertions.assertEquals(parentJDO1.getRoles(), parentDTO1.getRoles());
		System.out.println("****************************** JDO Mapping test ended ***********************************");


	}

}
