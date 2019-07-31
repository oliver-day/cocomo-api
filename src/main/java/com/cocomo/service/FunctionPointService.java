package com.cocomo.service;

import com.cocomo.pojo.FPDefault;
import com.cocomo.bo.FPDefaultBO;

import java.util.List;

public interface FunctionPointService {

    /**
     * Get Default Function Point Complexity Weights
     * @author Oliver Day
     * @date 03/29/19
     */
    List<FPDefault> getDefaultFunctionPoints();

    /**
     * Check whether a Function Point Type exists
     * or not
     * @author Oliver Day
     * @date 03/31/19
     */
    int checkFunctionPointType(String FunctionPointType);

    /**
     * Update Default Function Point Complexity Weights
     * @author Oliver Day
     * @date 03/29/19
     */
    Boolean updateDefaultFunctionPoints(FPDefaultBO fpDefaultBO);
}
