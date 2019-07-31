package com.cocomo.util;

/**
 * Util class for input params.
 * @author Haoming Zhang
 * @date 2/26/19 19:45
 */
public class ParamUtil {

    /**
     * Verify whether a value is
     * in a given range or not.
     * @author Haoming Zhang
     * @date 3/1/19 16:24
     */
    public static boolean rangeInDefined(int current, int min, int max) {
        return Math.max(min, current) == Math.min(current, max);
    }
}
