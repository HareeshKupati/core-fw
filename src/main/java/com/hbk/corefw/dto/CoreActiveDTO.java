package com.hbk.corefw.dto;

import java.util.Calendar;

public class CoreActiveDTO extends CoreIdDTO {
    private Calendar discontinueDate;

    public CoreActiveDTO() {
        super();
    }

    public CoreActiveDTO(Long id, Calendar discontinueDate) {
        super(id);
        this.discontinueDate = discontinueDate;
    }


    public Calendar getDiscontinueDate() {
        return discontinueDate;
    }

    public void setDiscontinueDate(Calendar discontinueDate) {
        this.discontinueDate = discontinueDate;
    }

    public boolean getIsActive(){
        return this.discontinueDate == null || (discontinueDate != null && discontinueDate.after(Calendar.getInstance()));
    }

}
