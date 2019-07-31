package com.cocomo.service.Impl;

import com.cocomo.bo.DefaultBO;
import com.cocomo.bo.SFactorBO;
import com.cocomo.common.Const;
import com.cocomo.dao.ProjectMapper;
import com.cocomo.dao.SDefaultMapper;
import com.cocomo.pojo.SDefault;
import com.cocomo.service.ScaleFactorService;
import com.cocomo.util.DefaultUtil;
import com.cocomo.util.ParamUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * The Implementation of ScaleFactorService
 * @author Haoming Zhang
 * @date 2/26/19 17:08
 */
@Service("scaleFactorService")
public class ScaleFactorServiceImpl implements ScaleFactorService {

    @Autowired
    private SDefaultMapper sDefaultMapper;

    @Autowired
    private ProjectMapper projectMapper;

    /**
     * Calculate scale factor.
     * @author Haoming Zhang
     * @date 2/26/19 20:32
     */
    @Override
    public Double calculateScaleFactor(SFactorBO sFactorBO) throws ClassNotFoundException,
                                                                   NoSuchMethodException,
                                                                   InvocationTargetException,
                                                                   IllegalAccessException {

        Class sDefaultClazz = Class.forName("com.cocomo.pojo.SDefault");
        Class sFactorBOClazz = Class.forName("com.cocomo.bo.SFactorBO");

        List<SDefault> sDefaults = sDefaultMapper.selectAllSDefault();
        Double result = 0.0;

        for (SDefault sDefault : sDefaults) {
            String getDriverName = DefaultUtil.getMethod(sDefault.getName(), "get");
            Method driverNameMethod = sFactorBOClazz.getDeclaredMethod(getDriverName);
            Integer sFactorIndex = (Integer) driverNameMethod.invoke(sFactorBO);
            String factorLevel = Const.DriverLevel.getValue(sFactorIndex);

            String getFactorLevel = DefaultUtil.getMethod(factorLevel, "get");
            Method factorLevelMethod = sDefaultClazz.getDeclaredMethod(getFactorLevel);
            result += (Double)factorLevelMethod.invoke(sDefault);
        }

        return result;
    }

    /**
     * Check whether a driver name
     * exists or not.
     * @author Haoming Zhang
     * @date 3/1/19 20:05
     */
    @Override
    public int checkDriverName(String driverName) {
        return sDefaultMapper.checkDriverName(driverName);
    }

    /**
     * Update a default scale factor by user input.
     * @author Haoming Zhang
     * @date 3/1/19 15:38
     */
    @Override
    public Boolean updateScaleFactor(DefaultBO defaultBO) {
        int rowCount = sDefaultMapper.updateByUserInput(defaultBO.getDriverName(),
                Const.DriverLevel.getValue(defaultBO.getDriverLevel()),
                defaultBO.getValue());

        if (rowCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * Get Scale factors default values
     * @author Dongchul Choi
     * @date 03/24/19
     */
    @Override
    public List<SDefault> getSdefaults(){
        return sDefaultMapper.getSdefaults();
    }

}
