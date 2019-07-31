package com.cocomo.service.Impl;

import com.cocomo.bo.FPDefaultBO;
import com.cocomo.dao.FPDefaultMapper;
import com.cocomo.pojo.FPDefault;

import com.cocomo.service.FunctionPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("functionPointService")
public class FunctionPointServiceImpl implements FunctionPointService {

    @Autowired
    private FPDefaultMapper fpDefaultMapper;

    /**
     * Get Default Function Point Complexity Weights
     * @author Oliver Day
     * @date 03/29/19
     */
    @Override
    public List<FPDefault> getDefaultFunctionPoints() { return fpDefaultMapper.getDefaultFunctionPoints(); }

    /**
     * Check whether a Function Point Type exists
     * or not
     * @author Oliver Day
     * @date 03/31/19
     */
    @Override
    public int checkFunctionPointType(String functionPointType) { return fpDefaultMapper.checkFunctionPointType(functionPointType); }

    /**
     * Update Default Function Point Complexity Weights
     * @author Oliver Day
     * @date 03/29/19
     */
    @Override
    public Boolean updateDefaultFunctionPoints(FPDefaultBO fpDefaultBO) {
        int updatedRowCount = fpDefaultMapper.updateDefaultFunctionPoints(fpDefaultBO.getFunctionType(),
                                                                          fpDefaultBO.getLow(),
                                                                          fpDefaultBO.getAverage(),
                                                                          fpDefaultBO.getHigh());
        return updatedRowCount > 0;
    }
}
