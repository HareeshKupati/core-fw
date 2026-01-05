package com.hbk.corefw.test.dto;

import com.hbk.corefw.dto.CoreIdDTO;
import com.hbk.corefw.validation.field.annotation.NotEmpty;
import com.hbk.corefw.validation.field.annotation.NotNull;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ParentDTO extends CoreIdDTO {
    @NotEmpty(minLength = 2, maxLength = 3)
    private String name;
    private BigDecimal amount;
    private Date date;
    private Integer count;
    private Boolean isTest;
    private long prevId;
    private int index;
    @NotEmpty
    private List<String> roles;
    @NotNull
    private SuperParentDTO superParentDTO;
    @NotEmpty
    private List<ChildDTO> childDTOs;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Boolean getIsTest() {
        return isTest;
    }

    public void setIsTest(Boolean test) {
        isTest = test;
    }

    public long getPrevId() {
        return prevId;
    }

    public void setPrevId(long prevId) {
        this.prevId = prevId;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public SuperParentDTO getSuperParentDTO() {
        return superParentDTO;
    }

    public void setSuperParentDTO(SuperParentDTO superParentDTO) {
        this.superParentDTO = superParentDTO;
    }

    public List<ChildDTO> getChildDTOs() {
        return childDTOs;
    }

    public void setChildDTOs(List<ChildDTO> childDTOs) {
        this.childDTOs = childDTOs;
    }
}
