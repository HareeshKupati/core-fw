package com.hbk.corefw.jdo;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

import java.util.Calendar;

@MappedSuperclass
public class CoreActiveJDO extends CoreIdJDO {

    @Column(name = "discontinue_date")
    private Calendar discontinueDate;

    public CoreActiveJDO() {
        super();
    }

   public Calendar getDiscontinueDate() {
        return discontinueDate;
    }

    public void setDiscontinueDate(Calendar discontinueDate) {
        this.discontinueDate = discontinueDate;
    }

}
