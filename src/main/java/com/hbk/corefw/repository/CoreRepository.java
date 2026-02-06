package com.hbk.corefw.repository;

import com.hbk.corefw.jdo.CoreJDO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface CoreRepository<JDO extends CoreJDO, ID> extends JpaRepository<JDO, ID> {

}
