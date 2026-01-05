package com.hbk.corefw.jdo;

import com.hbk.corefw.dto.UserDetailsDTO;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Calendar;

@MappedSuperclass
public class CoreAuditJDO extends CoreIdJDO {

    @Column(name = "created_date")
    private Calendar createdDate;

    @Column(name = "updated_date")
    private Calendar updatedDate;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_by")
    private Long updatedBy;

    public CoreAuditJDO() {
        super();
    }

    @PrePersist
    public void prePersist() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsDTO)
            this.setCreatedBy(((UserDetailsDTO) principal).getId());
        this.setCreatedDate(Calendar.getInstance());
    }

    @PreUpdate
    public void preUpdate() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetailsDTO)
            this.setUpdatedBy(((UserDetailsDTO) principal).getId());
        this.setUpdatedDate(Calendar.getInstance());
    }

    public Calendar getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Calendar createdDate) {
        this.createdDate = createdDate;
    }

    public Calendar getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Calendar updatedDate) {
        this.updatedDate = updatedDate;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Long updatedBy) {
        this.updatedBy = updatedBy;
    }
}
