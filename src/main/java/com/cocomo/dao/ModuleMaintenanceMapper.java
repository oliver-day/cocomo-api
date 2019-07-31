package com.cocomo.dao;


import com.cocomo.pojo.ModuleMaintenance;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ModuleMaintenanceMapper {

    /**
     * Insert a new record to
     * module_maintenance table
     * @author Haoming Zhang
     * @date 4/5/19 18:38
     */
    int insert(ModuleMaintenance moduleMaintenance);

    /**
     * Check whether a module has
     * been calculated or not
     * @author Haoming Zhang
     * @date 4/5/19 18:38
     */
    int checkCalculated(@Param("projectId") Integer projectId,
                        @Param("moduleIndex") Integer moduleIndex);

    /**
     * Update module_maintenance
     * @author Haoming Zhang
     * @date 4/5/19 18:47
     */
    int updateByProjectIdAndModuleIndex(ModuleMaintenance moduleMaintenance);
    
    /**
     * Get maintenance
     * @author Haoming Zhang
     * @date 4/5/19 21:25
     */
    ModuleMaintenance getMaintenanceByProjectAndModuleIndex(@Param("projectId") Integer projectId,
                                                            @Param("moduleIndex") Integer moduleIndex);

    /**
     * Get all module maintenance
     * of a specific project
     * @author Haoming Zhang
     * @date 4/6/19 19:28
     */
    List<ModuleMaintenance> getModuleMaintenanceByProjectId (@Param("projectId") Integer projectId);

}
