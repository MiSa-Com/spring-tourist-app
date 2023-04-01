package com.ms.tourist_app.application.utils;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtil {
    private SecurityUtil() {
    }

    public static String getCurrentUserLogin() {
        RequestHeader requestHeader = BeanUtil.getBean(RequestHeader.class);
        return requestHeader.getUsername();
    }

    public static Long getCurrentUserLoginId() {
        RequestHeader requestHeader = BeanUtil.getBean(RequestHeader.class);
        return requestHeader.getId();
    }



}
