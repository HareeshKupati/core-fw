package com.hbk.corefw.test.repository;

import com.hbk.corefw.repository.CoreRepository;
import com.hbk.corefw.test.jdo.RoleJDO;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends CoreRepository<RoleJDO, Long> {
}
