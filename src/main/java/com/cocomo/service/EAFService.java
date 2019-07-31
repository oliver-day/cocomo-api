package com.cocomo.service;

import com.cocomo.bo.DefaultBO;
import com.cocomo.bo.EAFEarlyDesignBO;
import com.cocomo.bo.EafBO;
import com.cocomo.pojo.EAFDefault;
import com.cocomo.pojo.EAFEarlyDesignDefault;
import com.cocomo.pojo.EAFMaintenanceDefault;
import com.cocomo.pojo.SDefault;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EAFService {

    /**
     * Check whether a eaf
     * driver name exists or not.
     * @author Haoming Zhang
     * @date 3/1/19 20:03
     */
    int checkDriverName(String driverName);

    /**
     * Check whether a eaf_earlydesign
     * driver name exists or not.
     * @author Haoming Zhang
     * @date 3/8/19 19:32
     */
    int checkDriverNameForEarlyDesign(String driverName);

    /**
     * Check whether a eaf_maintenance
     * driver name exists or not.
     * @author Haoming Zhang
     * @date 3/13/19 14:41
     */
    int checkDriverNameForMaintenance(String driverName);

    /**
     * Update eaf defaults
     * @author Haoming Zhang
     * @date 3/4/19 19:45
     */
    Boolean updateEAFDefault(DefaultBO defaultBO);

    /**
     * Update schedule defaults
     * @author Haoming Zhang
     * @date 3/29/19 22:26
     */
    Boolean updateScheduleDefault(DefaultBO defaultBO);

    /**
     * Update eaf_earlydesign defaults
     * @author Haoming Zhang
     * @date 3/8/19 19:24
     */
    Boolean updateEAFEarlyDesignDefault(DefaultBO defaultBO);

    /**
     * Update eaf_maintenance defaults
     * @author Haoming Zhang
     * @date 3/13/19 15:09
     */
    Boolean updateMaintenanceDefault(DefaultBO defaultBO);
    
    /**
     * Calculate eaf for early
     * design projects
     * @author Haoming Zhang
     * @date 3/9/19 13:35
     */
    Double calculateEarlyDesignEAF(EAFEarlyDesignBO eafEarlyDesignBO);

    /**
     * Calculate eaf for post-
     * architecture projects
     * @author Haoming Zhang
     * @date 3/9/19 19:44
     */
    Double calculateEAF(EafBO eafBO);

    /**
     * Get eaf default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    List<EAFDefault> getEafDefault();

    /**
     * Get eaf early design default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    List<EAFEarlyDesignDefault> getEafEarlyDesignDefault();

    /**
     * Calculate eaf for maintenance
     * @author Haoming Zhang
     * @date 4/5/19 14:32
     */
    Double calculateMaintenanceEAF(EafBO eafBO);

    /**
     * Get eaf maintenance default values
     * @author Dongchul Choi
     * @date 2019-04-22
     */
    List<EAFMaintenanceDefault> getEafMaintenanceDefault();

}
