package com.hbk.corefw.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static com.hbk.corefw.util.CoreConstants.*;

public class ControllerUtils {

    public static Pageable getPageable(Integer size, Integer page, List<String> sortProperties) {
        return PageRequest.ofSize(size != null ? size : Integer.valueOf(DEFAULT_SIZE))
                .withPage(page != null ? page : Integer.valueOf(DEFAULT_PAGE))
                .withSort(getSort(sortProperties));
    }

    public static Sort getSort(List<String> sortProperties) {
        List<Sort.Order> sortOrders = new ArrayList<>(sortProperties.size());
        for (String sortProperty : sortProperties) {
            String[] sortPropertyParts = sortProperty.split(SPACE);
            String property = sortPropertyParts.length > 0 ? sortPropertyParts[0] : null;
            String direction = sortPropertyParts.length > 1 ? sortPropertyParts[1] : ASC;
            if (property != null)
                sortOrders.add(DESC.equals(direction) ? Sort.Order.desc(property) : Sort.Order.asc(property));
        }
        return Sort.by(sortOrders);
    }
}
