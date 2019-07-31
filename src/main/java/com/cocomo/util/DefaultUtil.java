package com.cocomo.util;

/**
 * Utilities for default values
 * @author Haoming Zhang
 * @date 2/26/19 19:45
 */
public class DefaultUtil {

    /**
     * Acquire the function name for Getter/Setter
     * according to the field name.
     * @author Haoming Zhang
     * @date 2/26/19 20:06
     */
    public static String getMethod(String fieldName, String type) {
        String name = fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
        return type + name;
    }
}
