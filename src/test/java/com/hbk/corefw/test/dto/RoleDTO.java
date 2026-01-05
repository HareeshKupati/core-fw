package com.hbk.corefw.test.dto;

import com.hbk.corefw.dto.CoreDTO;
import com.hbk.corefw.validation.field.annotation.NotEmpty;

public class RoleDTO extends CoreDTO {
    private Long id;
    @NotEmpty
    private String roleName;
    @NotEmpty
    private String description;

    public RoleDTO() {
    }

    public RoleDTO(Long id, String roleName, String description) {
        this.id = id;
        this.roleName = roleName;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
