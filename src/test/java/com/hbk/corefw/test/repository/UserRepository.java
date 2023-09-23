package com.hbk.corefw.test.repository;

import com.hbk.corefw.repository.CoreRepository;
import com.hbk.corefw.test.jdo.UserJDO;
import org.springframework.stereotype.Repository;

@Repository("userTestRepository")
public interface UserRepository extends CoreRepository<UserJDO,Long> {
}
