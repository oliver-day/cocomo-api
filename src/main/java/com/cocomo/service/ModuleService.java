package com.cocomo.service;

import com.cocomo.bo.FPInfoBO;
import com.cocomo.bo.SlocBO;
import com.cocomo.pojo.Module;

import java.util.List;

/**
 * Module service
 * @author Haoming Zhang
 * @date 3/9/19 00:05
 */
public interface ModuleService {

    /**
     * Create a new module
     * @author Haoming Zhang
     * @date 3/9/19 00:09
     */
    Boolean createModule(Module module);

    /**
     * Get the amount of module
     * a specific project has
     * @author Haoming Zhang
     * @date 3/9/19 10:46
     */
    int getTotalModuleByProjectId(Integer projectId);

    /**
     * Get all of the module
     * names that belongs to
     * a specific project.
     * @author Haoming Zhang
     * @date 3/9/19 10:49
     */
    List<String> getModuleNamesByProjectId(Integer projectId);

    /**
     * Check whether a module exist
     * @author Haoming Zhang
     * @date 3/11/19 23:21
     */
    int checkModule(Integer projectId, Integer moduleIndex);

    /**
     * Deletes all modules with
     * the provided projectId
     * @param projectId
     * @return
     * @uather Oliver Day
     * @date 03/15/19
     */
    Boolean deleteModulesByProjectId(Integer projectId);

    /**
     * Delete a module and update
     * the module_index that belongs
     * to the same project
     * @author Haoming Zhang
     * @date 3/12/19 00:27
     */
    Boolean deleteModule(Integer projectId, Integer moduleIndex);

    /**
     * Get details of a specific module in a project by project id and module index
     * GET http://cocomo-api.com/projects/{projectId}/modules/{moduleIndex}
     * @author Dongchul Choi
     * @date 03/12/19
     */
    Module getModuleRowByProjectId(Integer projectId, Integer moduleIndex);

    /**
     * Get modules of a project with projectId
     * @param projectId
     * @return list of modules
     * @author Oliver Day
     * @date 03/15/19
     */
    List<Module> getModulesByProjectId(Integer projectId);

    /**
     * Get the module name of a
     * specific project
     * @author Haoming Zhang
     * @date 3/12/19 14:42
     */
    String getModuleNameByProjectIdAndModuleIndex(Integer projectId, Integer moduleIndex);

    /**
     * Update EAF of specific module by project Id and module index
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/eaf
     * @author Dongchul Choi
     * @date 03/21/19
     * Notice that there are 2 stages, and the cost drivers are different.
     */
    int updateModuleEaf(Integer projectId, Integer moduleIndex, Double eaf);

    /**
     * Update Module Name of a specific module of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/module_name
     * @author Dongchul Choi
     * @date 2019-03-26
     */
    int updateModuleName(Integer projectId, Integer moduleIndex, String name);

    /**
     * Update labor of a specific module of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/labor
     * @author Dongchul Choi
     * @date 2019-03-26
     */
    int updateModuleLabor(Integer projectId, Integer moduleIndex, Double labor);
    /**
     * Update EAF of all modules with give project id
     * that has had its schedule value changed
     * @authoer Oliver Day
     * @date 03/26/19
     * @return
     */
    Boolean updateEAFOfModulesDueToScheduleChange(Integer projectId, Double oldSCHEDValue, Double newSCHEDValue);

    /**
     * Update size of a specific module of a specific project
     * @author Oliver Day
     * @date 03/27/19
     * @return
     */
    int updateModuleSize(Integer projectId, Integer moduleIndex, Integer moduleSize);

    /**
    * Get the module size for a specific module of a specific project
    * @author Oliver Day
    * @date 03/28/19
    * @return module size
    */
    int getModuleSize(Integer projectId, Integer moduleIndex);

    /**
     * Get the module EAF for a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return module eaf
     */
    Double getModuleEAF(Integer projectId, Integer moduleIndex);

    /**
     * Get the module programming language for a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return module language
     */
    String getModuleLanguage(Integer projectId, Integer moduleIndex);

    /**
     * Get the module labor costs for a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return module labor
     */
    Double getModuleLabor(Integer projectId, Integer moduleIndex);

    /**
     * Update programming language of a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return count of modules updated
     */
    int updateModuleLanguage(Integer projectId, Integer moduleIndex, String moduleLanguage);

    /**
     * Calculate Module Size by SLOC
     * @author Oliver Day
     * @date 03/31/19
     * @return equivalent SLOC
     */
    int calculateModuleSizeBySLOC(SlocBO slocBO);

    /**
     * Calculate Module Size by Function Points
     * @author Oliver Day
     * @date 04/01/19
     * @return equivalent SLOC
     */
    int calculateModuleSizeByFunctionPoints(FPInfoBO fpInfoBO);

    /**
     * Get the module cost for a specific module of a specific project
     * @author Haoming Zhang
     * @date 4/5/19 21:05
     */
    Double getModuleCost(Integer projectId, Integer moduleIndex);

    /**
     * Get the nominal PM for a specific module of a specific project
     * @author Haoming Zhang
     * @date 4/5/19 21:09
     */
    Double getModuleNominalPM(Integer projectId, Integer moduleIndex);
}
