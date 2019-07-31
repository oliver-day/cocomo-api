package com.cocomo.dao;

import com.cocomo.bo.FPDefaultBO;
import com.cocomo.pojo.FPDefault;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface FPDefaultMapper {

    /**
     * Get Default Function Point Complexity Weights
     * for VO
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
    int checkFunctionPointType(@Param("functionPointType") String functionPointType);

    /**
     * Update Default Function Point Complexity Weights
     * @author Oliver Day
     * @date 03/29/19
     */
    int updateDefaultFunctionPoints(@Param("functionPointType") String functionPointType,
                                    @Param("lowWeight") Integer lowWeight,
                                    @Param("aveWeight") Integer aveWeight,
                                    @Param("highWeight") Integer highWeight);

    /**
     * Get all the Default Function Points Complexity Weights
     * for Calculation
     * @author Oliver Day
     * @date 03/31/19
     */
    List<Map<String, Object>> getFPComplexityWeights();
}
