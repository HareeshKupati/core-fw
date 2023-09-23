package com.hbk.corefw.test.jdo;

import com.hbk.corefw.jdo.CoreJDO;

import javax.persistence.*;

@Entity
@Table(name = "role")
public class RoleJDO extends CoreJDO {
    private Long id;
    private String roleName;
    private String description;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Column(name = "rolename", unique = true, nullable = false)
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }


    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
