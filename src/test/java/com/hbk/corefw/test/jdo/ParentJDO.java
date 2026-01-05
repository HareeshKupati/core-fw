package com.hbk.corefw.test.jdo;

import com.hbk.corefw.jdo.CoreIdJDO;
import com.hbk.corefw.jdo.CoreJDO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ParentJDO extends CoreIdJDO {
    private String name;
    private BigDecimal amount;
    private Date date;
    private Integer count;
    private Boolean isTest;
    private long prevId;
    private int index;
    private List<String> roles;
    private SuperParentJDO superParentJDO;
    private List<ChildJDO> childJDOs;

    public ParentJDO() {
    }


    public ParentJDO(Long id, String name, BigDecimal amount, Date date, Integer count, Boolean isTest, long prevId, int index,
                     List<String> roles, SuperParentJDO superParentJDO, List<ChildJDO> childJDOs) {
        super(id);
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.count = count;
        this.isTest = isTest;
        this.prevId = prevId;
        this.index = index;
        this.roles = roles;
        this.superParentJDO = superParentJDO;
        this.childJDOs = childJDOs;
    }

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

    public SuperParentJDO getSuperParentJDO() {
        return superParentJDO;
    }

    public void setSuperParentJDO(SuperParentJDO superParentJDO) {
        this.superParentJDO = superParentJDO;
    }

    public List<ChildJDO> getChildJDOs() {
        return childJDOs;
    }

    public void setChildJDOs(List<ChildJDO> childJDOs) {
        this.childJDOs = childJDOs;
    }
}
