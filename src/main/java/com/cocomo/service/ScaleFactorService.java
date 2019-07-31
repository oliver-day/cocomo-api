package com.cocomo.service;

import com.cocomo.bo.DefaultBO;
import com.cocomo.bo.SFactorBO;
import com.cocomo.pojo.SDefault;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Service for scale factor
 * @author Haoming Zhang
 * @date 2/26/19 16:07
 */
public interface ScaleFactorService {

    /**
     * Calculate scale factor.
     * @author Haoming Zhang
     * @date 2/26/19 20:32
     */
    Double calculateScaleFactor(SFactorBO sFactorBO) throws ClassNotFoundException,
                                                            NoSuchMethodException,
                                                            InvocationTargetException,
                                                            IllegalAccessException;

    /**
     * Update the default value of
     * a given scale factor.
     * @author Haoming Zhang
     * @date 3/1/19 14:23
     */
    Boolean updateScaleFactor(DefaultBO defaultBO);

    /**
     * Check whether a driver name
     * exists or not.
     * @author Haoming Zhang
     * @date 3/1/19 20:03
     */
    int checkDriverName(String driverName);

    /**
     * Get Scale factors default values
     * @author Dongchul Choi
     * @date 03/24/19
     */
    List<SDefault> getSdefaults();

}
