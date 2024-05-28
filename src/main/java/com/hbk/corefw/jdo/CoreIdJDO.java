package com.hbk.corefw.jdo;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class CoreIdJDO extends CoreJDO {
    private Long id;

    public CoreIdJDO() {
        super();
    }

    public CoreIdJDO(Long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
