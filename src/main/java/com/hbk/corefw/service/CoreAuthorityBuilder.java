package com.hbk.corefw.service;

import com.hbk.corefw.util.CoreConstants;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Component
public class CoreAuthorityBuilder {

    public String getReadAuthority() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        List<String> paths = List.of(request.getServletPath().split("/"));
        String resourcePath = paths.get(paths.size() - 1);
        try {
            Long id = Long.valueOf(resourcePath);
            resourcePath = paths.get(paths.size() - 2);
        } catch (NumberFormatException e) {
        }
        return resourcePath + CoreConstants._READ;
    }

    public String getWriteAuthority()  {
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes())
                .getRequest();
        List<String> paths = List.of(request.getServletPath().split("/"));
        String resourcePath = paths.get(paths.size() - 1);
        try {
            Long id = Long.valueOf(resourcePath);
            resourcePath = paths.get(paths.size() - 2);
        } catch (NumberFormatException e) {
        }
        return resourcePath + CoreConstants._WRITE;
    }

}
