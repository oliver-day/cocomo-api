package com.cocomo.service.Impl;

import com.cocomo.bo.EquationDefaultBO;
import com.cocomo.dao.EquationFactorDefaultMapper;
import com.cocomo.pojo.EquationFactorDefault;
import com.cocomo.service.EquationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The implementation of
 * Equation service
 * @author Haoming Zhang
 * @date 3/13/19 16:48
 */
@Service("equationService")
public class EquationServiceImpl implements EquationService {

    @Autowired
    private EquationFactorDefaultMapper equationFactorDefaultMapper;

    /**
     * Update equation defaults
     * @author Haoming Zhang
     * @date 3/13/19 16:40
     */
    @Override
    public Boolean updateEquationDefault(EquationDefaultBO equationDefaultBO) {
        int count = equationFactorDefaultMapper.updateByUserInput(equationDefaultBO.getEquationName(),
                                                                  equationDefaultBO.getValue());
        return count > 0;
    }

    /**
     * Check whether a equation
     * exists or not
     * @author Haoming Zhang
     * @date 3/13/19 18:21
     */
    @Override
    public int checkEquation(String equationName) {
        return equationFactorDefaultMapper.checkEquationName(equationName);
    }

    /**
     * Get equation factor default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    @Override
    public List<EquationFactorDefault> getEquationFactorDefault(){ return equationFactorDefaultMapper.getEquationFactorDefault(); }

}
