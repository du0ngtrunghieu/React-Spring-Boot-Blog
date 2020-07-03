package com.trunghieu.common.util.constant;


public class APIConstants {
    /* 2xx Success*/
    public static final int SUCCESS_CODE = 200;
    public static final int FAIL_CODE = 500;
    public static final int CREATE_CODE = 201;
    public static final int ACCEPT_CODE = 202;

    /* 4xx Client errors */
    public static final int BAD_REQUEST_CODE = 400;
    public static final int UNAUTHORIZED_CODE = 401;
    public static final int FORBIDDEN_CODE = 403;
    public static final int NOT_FOUND_CODE = 404;
    /* 5xx Server errors */
    public static final int INTERNAL_SERVER_ERROR_CODE = 500;

    public static final String ERROR_UNKNOW_MSG = "ERROR UNKNOW MESSAGE";
    public static final String ERROR_ENTITY_NOT_FOUND = "ERROR ENTITY NOT FOUND";
    public static final String ERROR_UNKOWN = "ERROR UNKNOW";

    /* Paging */
    public static final String PAGE_SIZE_DEFAULT = "20";
    public static final String PAGE_NUMBER_DEFAULT = "1";

    /* REGEX */
    public static final String REGEX_EMAIL = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String REGEX_EXCEL2003 = "^.+\\.(?i)(xls)$";
    public static final String REGEX_EXCEL2007 = "^.+\\.(?i)(xlsx)$";
    public static final String REGEX_CSV = "^.+\\.(?i)(csv)$";
    public static final String SHEET_PAGE = "sheet1";
    public static final String REGEX_PHONE = "^(09|07|03|08|05)\\d{8}$";
    public static final String REGEX_FULLNAME = "^([a-zA-Z\\xC0-\\uFFFF]{1,60}[ \\-\\']{0,1}){1,6}$";
    public static final Integer PASSWORD_LENGTH = 6;
    public static final char DOT = '.';
    public static final char COMMA = ',';
    public static final char UNDERSCORE = '_';
    public static final String DASH = " - ";
    public static final String FORMATTER = "yyyy-MM-dd";
    public static final Integer MAX_PAGE_SIZE = 20;
    public static final String DEFAULT_PAGE_NUMBER = "0";
    public static final String DEFAULT_PAGE_SIZE = "10";


    public static final String PATH_FILE ="/api/v1/files/";
    public static final String PATH_MEDIA ="/api/v1/media/";


}
