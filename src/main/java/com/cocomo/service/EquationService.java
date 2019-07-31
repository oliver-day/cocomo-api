package com.cocomo.service;

import com.cocomo.bo.EquationDefaultBO;
import com.cocomo.pojo.EquationFactorDefault;

import java.util.List;

public interface EquationService {

    /**
     * Update equation defaults
     * @author Haoming Zhang
     * @date 3/13/19 16:40
     */
    Boolean updateEquationDefault(EquationDefaultBO equationDefaultBO);

    /**
     * Check whether a equation
     * exists or not
     * @author Haoming Zhang
     * @date 3/13/19 18:21
     */
    int checkEquation(String equationName);

    /**
     * Get equation factor default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    List<EquationFactorDefault> getEquationFactorDefault();

}
