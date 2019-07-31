package com.cocomo.service.Impl;

import com.cocomo.bo.FPCountsBO;
import com.cocomo.bo.FPInfoBO;
import com.cocomo.bo.SlocBO;
import com.cocomo.dao.FPDefaultMapper;
import com.cocomo.dao.ModuleMapper;
import com.cocomo.dao.ProjectMapper;
import com.cocomo.pojo.Module;
import com.cocomo.service.ModuleService;
import com.cocomo.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service("moduleService")
public class ModuleServiceImpl implements ModuleService {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private FPDefaultMapper fpDefaultMapper;

    /**
     * Create a new module
     * @author Haoming Zhang
     * @date 3/9/19 00:09
     */
    @Override
    public Boolean createModule(Module module) {
        int count = moduleMapper.insert(module);
        if (count > 0) {
            return true;
        }
        return false;
    }

    /**
     * Get the amount of module
     * a specific project has
     * @author Haoming Zhang
     * @date 3/9/19 10:46
     */
    @Override
    public int getTotalModuleByProjectId(Integer projectId) {
        return moduleMapper.getTotalModuleByProjectId(projectId);
    }

    /**
     * Get all of the module
     * names that belongs to
     * a specific project.
     * @author Haoming Zhang
     * @date 3/9/19 10:49
     */
    @Override
    public List<String> getModuleNamesByProjectId(Integer projectId) {
        return moduleMapper.getModuleNamesByProjectId(projectId);
    }

    /**
     * Check whether a module
     * exists or not
     * @author Haoming Zhang
     * @date 3/11/19 23:26
     */
    @Override
    public int checkModule(Integer projectId, Integer moduleIndex) {
        return moduleMapper.checkModule(projectId, moduleIndex);
    }

    /**
     * Deletes all modules with
     * the provided projectId
     * @param projectId
     * @return
     * @uather Oliver Day
     * @date 03/15/19
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean deleteModulesByProjectId(Integer projectId) {
        int deletedRows = moduleMapper.deleteModulesByProjectId(projectId);
        return deletedRows > 0;
    }

    /**
     * Delete a module and update
     * the module_index that belongs
     * to the same project
     * @author Haoming Zhang
     * @date 3/12/19 00:27
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean deleteModule(Integer projectId, Integer moduleIndex) {
        int deleteRow = moduleMapper.deleteModuleByProjectIdAndModuleIndex(projectId, moduleIndex);
        moduleMapper.updateModuleIndex(projectId, moduleIndex);
        projectService.calculateEstimate(projectId);
        return deleteRow > 0;
    }

    /**
     * Get details of a specific module in a project by project id and module index
     * GET http://cocomo-api.com/projects/{projectId}/modules/{moduleIndex}
     * @author Dongchul Choi
     * @date 03/12/19
     */
    @Override
    public Module getModuleRowByProjectId(Integer projectId, Integer moduleIndex){
        //return projectMapper.updateScaleFactorByProjectId(projectId, scaleFactor);
        return moduleMapper.getModuleRowByProjectId(projectId,moduleIndex);
    }

    public List<Module> getModulesByProjectId(Integer projectId) {
        return moduleMapper.getModulesByProjectId(projectId);
    }

    /**
     * Get the module name of a
     * specific project
     * @author Haoming Zhang
     * @date 3/12/19 14:42
     */
    @Override
    public String getModuleNameByProjectIdAndModuleIndex(Integer projectId, Integer moduleIndex) {
        return moduleMapper.getModuleNameByProjectIdAndModuleIndex(projectId, moduleIndex);
    }

    /**
     * Update EAF of specific module by project Id and module index
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/eaf
     * @author Dongchul Choi
     * @date 03/21/19
     * Notice that there are 2 stages, and the cost drivers are different.
     */
    public int updateModuleEaf(Integer projectId, Integer moduleIndex, Double eaf){
        return moduleMapper.updateModuleEaf(projectId, moduleIndex, eaf);
    }

    /**
     * Update Module Name of a specific module of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/module_name
     * @author Dongchul Choi
     * @date 2019-03-26
     */
    public int updateModuleName(Integer projectId, Integer moduleIndex, String name){
        return moduleMapper.updateModuleName(projectId, moduleIndex, name);
    }

    /**
     * Update labor of a specific module of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/labor
     * @author Dongchul Choi
     * @date 2019-03-26
     */
    public int updateModuleLabor(Integer projectId, Integer moduleIndex, Double labor){
        return  moduleMapper.updateModuleLabor(projectId, moduleIndex, labor);
    }
    /**
     * Update EAF of all modules with give project id
     * that has had its schedule value changed
     * @authoer Oliver Day
     * @date 03/26/19
     * @return
     */
    public Boolean updateEAFOfModulesDueToScheduleChange(Integer projectId, Double oldSCHEDValue, Double newSCHEDValue) {
        int updatedModuleRows = moduleMapper.updateModulesEAfDueToScheduleChange(projectId, oldSCHEDValue, newSCHEDValue);
        return updatedModuleRows > 0;
    }

    /**
     * Update size of a specific module of a specific project
     * @author Oliver Day
     * @date 03/27/19
     * @return
     */
    public int updateModuleSize(Integer projectId, Integer moduleIndex, Integer moduleSize) {
        return moduleMapper.updateModuleSize(projectId, moduleIndex, moduleSize);
    }

    /**
     * Get the module size for a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return module size
     */
    public int getModuleSize(Integer projectId, Integer moduleIndex) {
        return moduleMapper.getModuleSize(projectId, moduleIndex);
    }

    /**
     * Get the module EAF for a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return module eaf
     */
    public Double getModuleEAF(Integer projectId, Integer moduleIndex) {
        return moduleMapper.getModuleEAF(projectId, moduleIndex);
    }

    /**
     * Get the module programming language for a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return module language
     */
    public String getModuleLanguage(Integer projectId, Integer moduleIndex) {
        return moduleMapper.getModuleLanguage(projectId, moduleIndex);
    }

    /**
     * Get the module labor costs for a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return module labor
     */
    public Double getModuleLabor(Integer projectId, Integer moduleIndex) {
        return moduleMapper.getModuleLabor(projectId, moduleIndex);
    }

    /**
     * Update programming language of a specific module of a specific project
     * @author Oliver Day
     * @date 03/28/19
     * @return count of modules updated
     */
    public int updateModuleLanguage(Integer projectId, Integer moduleIndex, String moduleLanguage) {
        return moduleMapper.updateModuleLanguage(projectId, moduleIndex, moduleLanguage);
    }

    /**
     * Calculate Module Size by SLOC
     * @author Oliver Day
     * @date 03/31/19
     * @return equivalent SLOC
     */
    public int calculateModuleSizeBySLOC(SlocBO slocBO) {

        int newSloc = slocBO.getNewCodeSloc();
        int adaptedSloc = slocBO.getAdaptedCodeSloc();
        int dm = slocBO.getDesignModPerOfAsloc();
        int cm = slocBO.getCodeModPerOfAsloc();
        int im = slocBO.getIntegrationPerForAsloc();
        int aa = slocBO.getAssessmentAssimilationRating();

        int suLevel = slocBO.getSoftwareUnderstandingRating() - 1;
        int[] suRatings = new int[]{50, 40, 30, 20, 10};
        int su = suRatings[suLevel];

        int unfmLevel = slocBO.getProgrammerUnfamiliarityLevel();
        double[] unfmRatings = new double[]{0.0, 0.1, 0.2, 0.3, 0.4, 0.5, 0.6, 0.7, 0.8, 0.9, 1.0};
        double unfm = unfmRatings[unfmLevel];

        int at = slocBO.getAutomaticTranslationPer();

        // Calculate equivalentSLOC
        int equivalentSLOC = newSloc;
        if(adaptedSloc > 0) {
            double aaf = (0.4 * dm) + (0.3 * cm) + (0.3 * im);
            double aam = 0;

            if(aaf <= 50) {
                aam = (aa + aaf * (1 + (0.02 * (double)su * (double)unfm))) / 100;
            } else {
                aam = (aa + aaf + ((double)su * (double)unfm)) / 100;
            }

            equivalentSLOC += adaptedSloc * (1 - (double)at / 100) * aam;
        }
        return equivalentSLOC;
    }

    /**
     * Calculate Module Size by Function Points
     * @author Oliver Day
     * @date 04/01/19
     * @return equivalent SLOC
     */
    public int calculateModuleSizeByFunctionPoints(FPInfoBO fpInfoBO) {

        // Extract Function Point Counts from fpInfoBO
        List<FPCountsBO> fpCountsList = fpInfoBO.getFpCountsBOList();

        int[][] fpCountsMatrix = new int[5][3];
        fpCountsMatrix[0][0] = fpCountsList.get(0).getLow();
        fpCountsMatrix[0][1] = fpCountsList.get(0).getAverage();
        fpCountsMatrix[0][2] = fpCountsList.get(0).getHigh();

        fpCountsMatrix[1][0] = fpCountsList.get(1).getLow();
        fpCountsMatrix[1][1] = fpCountsList.get(1).getAverage();
        fpCountsMatrix[1][2] = fpCountsList.get(1).getHigh();

        fpCountsMatrix[2][0] = fpCountsList.get(2).getLow();
        fpCountsMatrix[2][1] = fpCountsList.get(2).getAverage();
        fpCountsMatrix[2][2] = fpCountsList.get(2).getHigh();

        fpCountsMatrix[3][0] = fpCountsList.get(3).getLow();
        fpCountsMatrix[3][1] = fpCountsList.get(3).getAverage();
        fpCountsMatrix[3][2] = fpCountsList.get(3).getHigh();

        fpCountsMatrix[4][0] = fpCountsList.get(4).getLow();
        fpCountsMatrix[4][1] = fpCountsList.get(4).getAverage();
        fpCountsMatrix[4][2] = fpCountsList.get(4).getHigh();

        // Get Default Function Point Complexity Weights
        List<Map<String,Object>> fpDefaults = fpDefaultMapper.getFPComplexityWeights();

        // Organize fpDefaults
        fpDefaults.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
            Integer id1 = Integer.valueOf(o1.get("id").toString());
            Integer id2 = Integer.valueOf(o2.get("id").toString());

            return id1.compareTo(id2);
        });

        int[][] fpComplexityWeights = new int[5][3];
        fpComplexityWeights[0][0] = (int) fpDefaults.get(0).get("low");
        fpComplexityWeights[0][1] = (int) fpDefaults.get(0).get("average");
        fpComplexityWeights[0][2] = (int) fpDefaults.get(0).get("high");

        fpComplexityWeights[1][0] = (int) fpDefaults.get(1).get("low");
        fpComplexityWeights[1][1] = (int) fpDefaults.get(1).get("average");
        fpComplexityWeights[1][2] = (int) fpDefaults.get(1).get("high");

        fpComplexityWeights[2][0] = (int) fpDefaults.get(2).get("low");
        fpComplexityWeights[2][1] = (int) fpDefaults.get(2).get("average");
        fpComplexityWeights[2][2] = (int) fpDefaults.get(2).get("high");

        fpComplexityWeights[3][0] = (int) fpDefaults.get(3).get("low");
        fpComplexityWeights[3][1] = (int) fpDefaults.get(3).get("average");
        fpComplexityWeights[3][2] = (int) fpDefaults.get(3).get("high");

        fpComplexityWeights[4][0] = (int) fpDefaults.get(4).get("low");
        fpComplexityWeights[4][1] = (int) fpDefaults.get(4).get("average");
        fpComplexityWeights[4][2] = (int) fpDefaults.get(4).get("high");

        // Get total Function Points Count
        int unadjustedFpCount = 0;
        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 3; ++j) {
                unadjustedFpCount += fpComplexityWeights[i][j] * fpCountsMatrix[i][j];
            }
        }

        // Get Unadjusted Function Points to SLOC Multiplier
        final int[] languageMultiplier = {
                38,  //Access
                71,  //Ada 83
                49,  //Ada 95
                49,  //AI Shell
                32,  //APL
                320, //Assembly - Basic
                213, //Assembly - Macro
                64,  //Basic - ANSI
                91,  //Basic - Compiled
                32,  //Basic - Visual
                128, //C
                55,  //C++
                91,  //Cobol(ANSI85)
                40,  //Database - Default
                4,   //Fifth Generation Language
                320, //First Generation Language
                64,  //Forth
                107, //Fortran 77
                71,  //Fortran 95
                20,  //Fourth Generation Language
                64,  //High Level Language
                15,  //HTML 3.0
                53,  //Java
                107, //Jovial
                64,  //Lisp
                640, //Machine Code
                80,  //Modula 2
                91,  //Pascal
                27,  //PERL
                16,  //PowerBuilder
                64,  //Prolog
                13,  //Query - Default
                80,  //Report Generator
                107, //Second Generation Language
                46,  //Simulation - Default
                6,   //Spreadsheet
                80,  //Third Generation Language
                107, //Unix Shell Scripts
                29,  //Visual Basic 5.0
                34   //Visual C++
        };

        // if Manual Multiplier, else Language Multiplier
        int ufpToSLOCMultiplier;
        if(fpInfoBO.getLanguage() == -1) {
            ufpToSLOCMultiplier = fpInfoBO.getManualMultiplier();
        } else {
            ufpToSLOCMultiplier = languageMultiplier[fpInfoBO.getLanguage()];
        }

        int equivalentSLOC = ufpToSLOCMultiplier * unadjustedFpCount;

        return equivalentSLOC;
    }

    /**
     * Get the module cost for a specific module of a specific project
     * @author Haoming Zhang
     * @date 4/5/19 21:05
     */
    @Override
    public Double getModuleCost(Integer projectId, Integer moduleIndex) {
        return moduleMapper.getCostByProjectAndModuleIndex(projectId, moduleIndex);
    }

    /**
     * Get the nominal PM for a specific module of a specific project
     * @author Haoming Zhang
     * @date 4/5/19 21:09
     */
    @Override
    public Double getModuleNominalPM(Integer projectId, Integer moduleIndex) {
        return moduleMapper.getNomEffortByProjectAndModuleIndex(projectId, moduleIndex);
    }
}
