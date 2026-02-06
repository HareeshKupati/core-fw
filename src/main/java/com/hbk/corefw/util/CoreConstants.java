package com.hbk.corefw.util;

public interface CoreConstants {
    String JDO = "JDO";
    String DTO = "DTO";

    String jdo = "jdo";
    String dto = "dto";
    String RESOURCE_NOT_FOUND = "Resource Not Found!!";
    String VALIDATION_ERROR = "Validation errors are found.";

    Integer NOT_FOUND = 404;
    Integer BAD_REQUEST = 400;
    Integer INTERNAL_SERVER_ERROR = 500;


    String ID_PARAM = "{id}";
    String SLASH = "/";
    String ID_PATH_PARAM = SLASH + ID_PARAM;

    String SIZE = "size";
    String DEFAULT_SIZE = "10";
    String PAGE = "page";
    String DEFAULT_PAGE = "0";
    String SORT = "sort";
    String DEFAULT_SORT = "id asc";

    String SPACE = " ";
    String DESC = "desc";
    String TRUE = "true";
    String ASC = "asc";


    String _READ = "_read";
    String _WRITE = "_write";
}

