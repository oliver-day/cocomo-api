package com.cocomo.service;

import com.cocomo.pojo.ModuleMaintenance;
import com.cocomo.vo.ProjectMaintenanceVO;

/**
 * Maintenance Service
 * @author Haoming Zhang
 * @date 4/5/19 15:03
 */
public interface MaintenanceService {

    /**
     * Verify whether a module has
     * been calculated or not
     * @author Haoming Zhang
     * @date 4/5/19 20:34
     */
    int checkCalculated(Integer projectId, Integer moduleIndex);


    /**
     * Method for calculate maintenance
     * of a specific module
     * @author Haoming Zhang
     * @date 4/5/19 15:05
     */
    Boolean calculateModuleMaintenance(Double maintenanceEaf,
                                     Double laborRate,
                                     Double lifeSpan,
                                     Double codeModification,
                                     Double codeAdded,
                                     Integer softwareUnderstanding,
                                     Double unfamiliarityWithSoftware,
                                     Integer projectId,
                                     Integer moduleIndex);

    /**
     * Get maintenance
     * @author Haoming Zhang
     * @date 4/5/19 21:25
     */
    ModuleMaintenance getMaintenanceByProjectAndModuleIndex(Integer projectId, Integer moduleIndex);

    /**
     * Assemble the result for
     * project level maintenance
     * @author Haoming Zhang
     * @date 4/6/19 18:36
     */
    ProjectMaintenanceVO assembleProjectMaintenance(Integer projectId);
}
