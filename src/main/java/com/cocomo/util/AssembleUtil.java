package com.cocomo.util;

import com.cocomo.pojo.EstimateRange;

/**
 * Utility class for assembling
 * objects.
 * @author Haoming Zhang
 * @date 3/17/19 16:10
 */
public class AssembleUtil {

    /**
     * Assemble estimate range
     * @author Haoming Zhang
     * @date 3/17/19 16:09
     */
    public static void assembleEstimateRange(EstimateRange estimateRange,
                                             double[] range) {
        estimateRange.setEffort(range[0]);
        estimateRange.setSched(range[1]);
        estimateRange.setProd(range[2]);
        estimateRange.setCost(range[3]);
        estimateRange.setInst(range[4]);
        estimateRange.setStaff(range[5]);
        estimateRange.setRisk(range[6]);
    }
}
