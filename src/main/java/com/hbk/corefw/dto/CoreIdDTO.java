package com.hbk.corefw.dto;

import java.util.Objects;

public class CoreIdDTO extends CoreDTO implements Comparable<CoreIdDTO> {
    private Long id;

    public CoreIdDTO() {
        super();
    }

    public CoreIdDTO(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int compareTo(CoreIdDTO coreIdDTO) {
        Long id1 = Objects.nonNull(this.id) ? this.id : 0l;
        Long id2 = Objects.nonNull(coreIdDTO.id) ? coreIdDTO.id : 0l;
        return Long.compare(id1, id2);
    }
}
