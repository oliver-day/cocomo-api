package com.cocomo.service.Impl;

import com.cocomo.common.Const;
import com.cocomo.dao.EquationFactorDefaultMapper;
import com.cocomo.dao.ModuleMaintenanceMapper;
import com.cocomo.dao.ModuleMapper;
import com.cocomo.dao.ProjectMapper;
import com.cocomo.pojo.Module;
import com.cocomo.pojo.ModuleMaintenance;
import com.cocomo.service.MaintenanceService;
import com.cocomo.vo.ProjectDetailsVO;
import com.cocomo.vo.ProjectMaintenanceVO;
import com.cocomo.vo.ProjectSummaryVO;
import org.apache.ibatis.javassist.compiler.ast.DoubleConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementation for
 * MaintenanceService
 * @author Haoming Zhang
 * @date 4/5/19 15:14
 */
@Service("maintenanceService")
public class MaintenanceServiceImpl implements MaintenanceService {

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EquationFactorDefaultMapper equationFactorDefaultMapper;

    @Autowired
    private ModuleMaintenanceMapper moduleMaintenanceMapper;

    /**
     * Verify whether a module has
     * been calculated or not
     * @author Haoming Zhang
     * @date 4/5/19 20:34
     */
    @Override
    public int checkCalculated(Integer projectId, Integer moduleIndex) {
        return moduleMaintenanceMapper.checkCalculated(projectId, moduleIndex);
    }

    /**
     * Method for calculate maintenance
     * of a specific module
     * @author Haoming Zhang
     * @date 4/5/19 15:05
     */
    @Override
    public Boolean calculateModuleMaintenance(Double maintenanceEaf,
                                            Double laborRate,
                                            Double lifeSpan,
                                            Double codeModification,
                                            Double codeAdded,
                                            Integer softwareUnderstanding,
                                            Double unfamiliarityWithSoftware,
                                            Integer projectId,
                                            Integer moduleIndex) {
        Double MAF = 1 + ((softwareUnderstanding * unfamiliarityWithSoftware) / 100);

        int devpSloc = moduleMapper.getModuleSize(projectId, moduleIndex);
        Double size = ((codeAdded * 0.01 * devpSloc)+(codeModification * 0.01 * devpSloc)) * MAF; //in SLOC

        Double B = equationFactorDefaultMapper.getDefaultValueById(1);
        Double scaleFactor = projectMapper.getScaleFactorByProjectId(projectId);
        Double E = B + (0.01 * scaleFactor);

        // Calculate module maintenance values
        Double pm = 2.94 * (Math.round((Math.round(Math.pow((size) / 1000, E) * 100000.0)
                            / 100000.0) * maintenanceEaf *10000.0)/10000.0);
        Double cost = pm * laborRate;

        // Assemble moduleMaintenance
        ModuleMaintenance moduleMaintenance = new ModuleMaintenance();
        moduleMaintenance.setCost(cost);
        moduleMaintenance.setPm(pm);
        moduleMaintenance.setLaborRate(laborRate);
        moduleMaintenance.setPercentageAdded(codeAdded);
        moduleMaintenance.setPercentageModified(codeModification);
        moduleMaintenance.setSoftwareUnderstanding(softwareUnderstanding);
        moduleMaintenance.setSoftwareUnfamiliarity(unfamiliarityWithSoftware);
        moduleMaintenance.setModuleIndex(moduleIndex);
        moduleMaintenance.setProjectId(projectId);

        // Insert or update table
        int count = moduleMaintenanceMapper.checkCalculated(projectId, moduleIndex);
        if (count == 0) {
            int insert = moduleMaintenanceMapper.insert(moduleMaintenance);
            return insert > 0;
        } else {
            int update = moduleMaintenanceMapper.updateByProjectIdAndModuleIndex(moduleMaintenance);
            return update > 0;
        }
    }

    /**
     * Get maintenance
     * @author Haoming Zhang
     * @date 4/5/19 21:25
     */
    @Override
    public ModuleMaintenance getMaintenanceByProjectAndModuleIndex(Integer projectId, Integer moduleIndex) {
        return moduleMaintenanceMapper.getMaintenanceByProjectAndModuleIndex(projectId, moduleIndex);
    }

    /**
     * Assemble the result for
     * project level maintenance
     * @author Haoming Zhang
     * @date 4/6/19 18:36
     */
    @Override
    public ProjectMaintenanceVO assembleProjectMaintenance(Integer projectId) {
        // Calculate netEffort
        Double A = equationFactorDefaultMapper.getDefaultValueById(2);
        Double B = equationFactorDefaultMapper.getDefaultValueById(1);
        Double scaleFactor = projectMapper.getScaleFactorByProjectId(projectId);
        Double E = B + (0.01 * scaleFactor);
        Double totalSloc = moduleMapper.getAggregateSloc(projectId);
        Double netEffort = (A * Math.pow(totalSloc / 1000, E));

        // Get all modules that belong to a project
        List<String> names = moduleMapper.getModuleNamesByProjectId(projectId);
        List<Module> modules = moduleMapper.getModulesByProjectId(projectId);
        Integer slocCum = 0;
        Double devPmCum = 0.0;
        Double devCost = 0.0;

        for (Module module : modules) {
            Integer sloc = module.getSize();
            slocCum += sloc;
            Double eaf = module.getEaf();
            Double laborCharge = module.getLabor();
            Double pm = netEffort * (sloc / totalSloc) * eaf;
            devPmCum += pm;
            devCost += (laborCharge * pm);
        }

        // Assemble project details
        ProjectDetailsVO projectDetailsVO = new ProjectDetailsVO();
        projectDetailsVO.setModuleNames(names);
        projectDetailsVO.setDevelopmentSLOC(slocCum);
        projectDetailsVO.setPersonMonth(Double.valueOf(Const.df2.format(devPmCum)));
        projectDetailsVO.setDevelopmentCost(Double.valueOf(Const.df2.format(devCost)));

        // Get all module maintenance
        List<ModuleMaintenance> moduleMaintenanceList = moduleMaintenanceMapper.
                                                        getModuleMaintenanceByProjectId(projectId);
        Double cumPM = 0.0;
        Double cumCost = 0.0;

        if (moduleMaintenanceList.size() != 0) {
            for (ModuleMaintenance moduleMaintenance : moduleMaintenanceList) {
                cumPM += moduleMaintenance.getPm();
                cumCost += moduleMaintenance.getCost();
            }
        }

        // Assemble project summary
        ProjectSummaryVO projectSummaryVO = new ProjectSummaryVO();
        projectSummaryVO.setCumulativeMaintenancePM(Double.valueOf(Const.df2.format(cumPM)));
        projectSummaryVO.setOverallPM(Double.valueOf(Const.df2.format(cumPM + devPmCum)));
        projectSummaryVO.setCumulativeMaintenanceCost(Double.valueOf(Const.df2.format(cumCost)));
        projectSummaryVO.setOverallCost(Double.valueOf(Const.df2.format(cumCost + devCost)));

        // Assemble result
        ProjectMaintenanceVO projectMaintenanceVO = new ProjectMaintenanceVO();
        projectMaintenanceVO.setProjectDetails(projectDetailsVO);
        projectMaintenanceVO.setSummaryOfProject(projectSummaryVO);

        return projectMaintenanceVO;
    }
}
