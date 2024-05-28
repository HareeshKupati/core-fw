package com.hbk.corefw.test.jdo;

import com.hbk.corefw.jdo.CoreIdJDO;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ChildJDO extends CoreIdJDO {
    private String name;
    private BigDecimal amount;
    private Date date;
    private Integer count;
    private Boolean isTest;
    private long prevId;
    private int index;
    private List<String> roles;

    public ChildJDO() {
    }


    public ChildJDO(Long id, String name, BigDecimal amount, Date date, Integer count, Boolean isTest, long prevId, int index, List<String> roles) {
        super(id);
        this.name = name;
        this.amount = amount;
        this.date = date;
        this.count = count;
        this.isTest = isTest;
        this.prevId = prevId;
        this.index = index;
        this.roles = roles;
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

}
