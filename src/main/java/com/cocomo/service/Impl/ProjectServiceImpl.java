package com.cocomo.service.Impl;

import com.cocomo.bo.SFactorBO;
import com.cocomo.bo.ScheduleBO;
import com.cocomo.common.Const;
import com.cocomo.dao.*;
import com.cocomo.pojo.*;
import com.cocomo.service.ProjectService;
import com.cocomo.util.AssembleUtil;
import com.cocomo.vo.GeneratedAttributeVO;
import org.apache.ibatis.javassist.compiler.ast.DoubleConst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * The Implementation of ProjectService.
 * @author Haoming Zhang
 * @date 2/26/19 00:44
 */
@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private EstimateRangeMapper estimateRangeMapper;

    @Autowired
    private EquationFactorDefaultMapper equationFactorDefaultMapper;

    @Autowired
    private ModuleMapper moduleMapper;

    @Autowired
    private SDefaultMapper sDefaultMapper;

    @Autowired
    private EAFDefaultMapper eafDefaultMapper;

    @Autowired
    private EAFEarlyDesignDefaultMapper eafEarlyDesignDefaultMapper;

    /**
     * Create a new project.
     * @author Haoming Zhang
     * @date 3/2/19 17:12
     */
    @Override
    public ResponseEntity createProject(Project project) {
        int count = projectMapper.insert(project);
        if (count > 0) {
            GeneratedAttributeVO generatedAttributeVO = new GeneratedAttributeVO();
            generatedAttributeVO.setId(project.getId());
            return new ResponseEntity<>(generatedAttributeVO, HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to insert a new project", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Delete a project with projectId
     * @param projectId
     * @return
     * @uather Oliver Day
     * @date 03/15/19
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean deleteProjectByProjectId(Integer projectId) {
        int deletedRows = projectMapper.deleteProjectByProjectId(projectId);
        return deletedRows > 0;
    }

    /**
     * Update project notes.
     * @author Haoming Zhang
     * @date 3/2/19 19:56
     */
    @Override
    public ResponseEntity updateProjectNotes(Integer projectId, String notes) {
        int count = projectMapper.updateNotesByProjectId(projectId, notes);
        if (count > 0) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get scheule of a specific project
     * @author Oliver Day
     * @data 03/06/19
     */
    @Override
    public Double getScheduleByProjectId(Integer projectId) {
        return projectMapper.getScheduleByProjectId(projectId);
    }

    /**
     * Update schedule of a
     * specific project
     * @author Oliver Day
     * @date 03/20/19
     */
    @Override
    public Boolean updateScheduleByProjectId(Integer projectId, Integer scheduleLevel, Double scheduleValue) {
        int count1 = projectMapper.updateScheduleLevelByProjectId(projectId, scheduleLevel);
        int count2 = projectMapper.updateScheduleValueByProjectId(projectId, scheduleValue);
        if (count1 > 0 && count2 > 0) {
            return true;
        }
        return false;
    }


    /**
     * Check whether a project exists or not.
     * @author Haoming Zhang
     * @date 3/2/19 19:56
     */
    @Override
    public int checkProject(Integer projectId) {
        return projectMapper.checkProject(projectId);
    }

    /**
     * Get the scale factor of
     * a specific project.
     * @author Haoming Zhang
     * @date 2/27/19 21:15
     */
    @Override
    public Double getScaleFactorByProjectId(Integer projectId) {
        return projectMapper.getScaleFactorByProjectId(projectId);
    }

    /**
     * Get the stage of
     * a specific project.
     * @author Haoming Zhang
     * @date 3/9/19 18:15
     */
    @Override
    public Integer getStageByProjectId(Integer projectId) {
        return projectMapper.getStageByProjectId(projectId);
    }

    /**
     * Get the note of
     * a specific project.
     * GET http://cocomo-api.com/projects/{pid}/notes
     * @author Dongchul Choi
     * @date 03/05/19
     */
    @Override
    public String getNotesByProjectId(Integer projectId){
        return projectMapper.getNotesByProjectId(projectId);
    }

    /**
     * Update Scale Factor of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/scale_factor
     * @author Dongchul Choi
     * @date 03/09/19
     */
    @Override
    public Integer updateProjectScaleFactor(Integer projectId, Double scaleFactor){
        return projectMapper.updateScaleFactorByProjectId(projectId, scaleFactor);
    }

    /**
     * Delete the estimate ranges
     * of a specific project
     * @param projectId
     * @return
     * @author Oliver Day
     * @data 03/15/19
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean deleteEstimatedRangeByProjectId(Integer projectId) {
        int deletedRows = estimateRangeMapper.deleteEstimatedRangeByProjectId(projectId);
        return deletedRows > 0;
    }

    /**
     * Get the pessimistic
     * range of a specific
     * project
     * @author Haoming Zhang
     * @date 3/12/19 21:07
     */
    @Override
    public EstimateRange getPessimisticByProjectId(Integer projectId) {
        return estimateRangeMapper.getPessimisticByProjectId(projectId);
    }

    /**
     * Get the most likely
     * range of a specific
     * project
     * @author Haoming Zhang
     * @date 3/12/19 21:35
     */
    @Override
    public EstimateRange getMostLikelyByProjectId(Integer projectId) {
        return estimateRangeMapper.getMostLikelyByProjectId(projectId);
    }

    /**
     * Get the optimistic
     * range of a specific
     * project
     * @author Haoming Zhang
     * @date 3/12/19 21:42
     */
    @Override
    public EstimateRange getOptimisticByProjectId(Integer projectId) {
        return estimateRangeMapper.getOptimisticByProjectId(projectId);
    }

    /**
     * Check whether the estimate
     * range of a specific project
     * has been calculated or not
     * @author Haoming Zhang
     * @date 3/12/19 23:02
     */
    @Override
    public int checkCalculated(Integer projectId) {
        return estimateRangeMapper.checkCalculated(projectId);
    }

    /**
     * Calculate the estimate range
     * of a specific project as well
     * as certain columns of modules
     * that belong to this project.
     * @author Haoming Zhang
     * @date 3/16/19 22:29
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void calculateEstimate(Integer projectId) {

        // For calculating estimate range
        double[] rangeData = new double[7];

        // Get default values for calculation
        Integer stage = projectMapper.getStageByProjectId(projectId);
        Double A = 0.0;
        if (stage.equals(Const.ProjectStage.EARLY_DESIGN)) {
            A = equationFactorDefaultMapper.getDefaultValueById(3);
        } else {
            A = equationFactorDefaultMapper.getDefaultValueById(2);
        }

        Double B = equationFactorDefaultMapper.getDefaultValueById(1);
        Double C = equationFactorDefaultMapper.getDefaultValueById(4);
        Double D = equationFactorDefaultMapper.getDefaultValueById(5);


        // Calculation
        Double scaleFactor = projectMapper.getScaleFactorByProjectId(projectId);
        Double SCHEDFactor = projectMapper.getScheduleByProjectId(projectId);
        Double E = B + (0.01 * scaleFactor);
        Double F = D + 0.2 * (E - B);
        Double totalSloc = moduleMapper.getAggregateSloc(projectId);
        // When there are no mudules left for a project, clear estimate range,
        // and set project code line and effort to be 0.
        if (totalSloc == null) {
            projectMapper.updateTotalLinesByProjectId(projectId, 0);
            projectMapper.updateEffortByProjectId(projectId,0);
            estimateRangeMapper.deleteEstimatedRangeByProjectId(projectId);
        } else {
            Double netEffort = (A * Math.pow(totalSloc / 1000, E));
            Integer schedRating = projectMapper.getScheduleLevelByProjectId(projectId);
            Double modStaff = 0.0;

            // Get all modules that belong to a specific project
            List<Module> modules = moduleMapper.getModulesByProjectId(projectId);
            double prevTDev = 0.0;
            for (Module module : modules) {
                Integer modSloc = module.getSize();
                double tDev = 0.0;

                if (modSloc > 0.0) {
                    Double modEaf = module.getEaf();
                    Double modLabor = module.getLabor();

                    Double modEsteff = netEffort * (modSloc / totalSloc) * modEaf;
                    module.setEstEffortDev(Double.valueOf(Const.df2.format(modEsteff)));

                    Double modNomeff = modEsteff/SCHEDFactor * 1.00;
                    module.setNomEffortDev(Double.valueOf(Const.df2.format(modNomeff)));

                    Double modCost = modLabor * modEsteff;
                    module.setCost(Double.valueOf(Const.df2.format(modCost)));

                    Double modInstCost = modCost / modSloc;
                    module.setInstCost(Double.valueOf(Const.df2.format(modInstCost)));

                    tDev = C * Math.pow(modNomeff, F);
                    tDev *= Const.getPercent(schedRating);
                    if (tDev > 0.0) {
                        module.setStaff(Double.valueOf(Const.df2.format(tDev)));
                    }

                    if (modEsteff > 0.0) {
                        Double modProd = modSloc / modEsteff;
                        module.setProd(Double.valueOf(Const.df2.format(modProd)));
                    }

                    rangeData[0] = modEsteff;
                    rangeData[3] = modCost;
                    prevTDev = tDev;
                } else {
                    tDev = prevTDev;
                }

                rangeData[1] = tDev;

                if (rangeData[0] > 0.0) {
                    rangeData[2] = totalSloc / rangeData[0];
                }
                modStaff = 0.0;

                for (Module moduleData : modules) {
                    Double staff = 0.0;
                    Double effort = moduleData.getEstEffortDev();
                    if (effort > 0.0) {
                        staff = Double.valueOf(Const.df2.format(effort / rangeData[1]));
                        staff *= Const.getCoefficient(schedRating);
                        staff = Math.round(staff * 100.0)/100.0;
                        moduleData.setStaff(staff);
                    }
                    modStaff += staff;
                }

                if (totalSloc > 0.0) {
                    rangeData[4] = rangeData[3] / totalSloc;
                }
                rangeData[5] = modStaff;
                projectMapper.updateTotalLinesByProjectId(projectId, totalSloc.intValue());
                projectMapper.updateEffortByProjectId(projectId, (int) rangeData[0]);

                setEstimateRange(projectId, rangeData, C, F, totalSloc);

                //TODO: addtolist()
            }
            // Update module columns
            for (Module moduleData : modules) {
                moduleMapper.updateCalculatedColumnsByPrimaryKey(moduleData);
            }
        }
    }

    /**
     * Set the estimate range of
     * a specific project
     * @author Haoming Zhang
     * @date 3/17/19 15:26
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void setEstimateRange(Integer projectId,
                                 double[] range,
                                 Double C,
                                 Double F,
                                 Double projectSloc) {
        // Calculate data
        double[] pessiData = new double[7];
        double[] optimData = new double[7];

        pessiData[0] = range[0] * 1.25;
        optimData[0] = range[0] * 0.8;

        pessiData[1] = C * Math.pow(pessiData[0], F);
        optimData[1] = C * Math.pow(optimData[0], F);

        pessiData[3] = range[3] * 1.25;
        optimData[3] = range[3] * 0.8;

        if(projectSloc > 0.0) {
            pessiData[2] = projectSloc / pessiData[0];
            optimData[2] = projectSloc / optimData[0];

            pessiData[4] = pessiData[3] / projectSloc;
            optimData[4] = optimData[3] / projectSloc;
        }

        if (pessiData[1] > 0.0) {
            pessiData[5] = pessiData[0] / pessiData[1];
        }
        if (optimData[1] > 0.0) {
            optimData[5] = optimData[0] / optimData[1];
        }

        // Set estimate range
        int count = estimateRangeMapper.checkCalculated(projectId);
        if (count > 0) {
            EstimateRange mostLikelyRange = estimateRangeMapper.getMostLikelyByProjectId(projectId);
            AssembleUtil.assembleEstimateRange(mostLikelyRange, range);

            EstimateRange pessiRange = estimateRangeMapper.getPessimisticByProjectId(projectId);
            AssembleUtil.assembleEstimateRange(pessiRange, pessiData);

            EstimateRange optimRange = estimateRangeMapper.getOptimisticByProjectId(projectId);
            AssembleUtil.assembleEstimateRange(optimRange, optimData);

            estimateRangeMapper.updateByPrimaryKey(pessiRange);
            estimateRangeMapper.updateByPrimaryKey(mostLikelyRange);
            estimateRangeMapper.updateByPrimaryKey(optimRange);
        } else {
            EstimateRange mostLikelyRange = new EstimateRange();
            mostLikelyRange.setType(Const.EstimateRangeType.MOSTLIKELY);
            mostLikelyRange.setProjectId(projectId);
            AssembleUtil.assembleEstimateRange(mostLikelyRange, range);

            EstimateRange pessiRange = new EstimateRange();
            pessiRange.setType(Const.EstimateRangeType.PESSIMISTIC);
            pessiRange.setProjectId(projectId);
            AssembleUtil.assembleEstimateRange(pessiRange, pessiData);

            EstimateRange optimRange = new EstimateRange();
            optimRange.setType(Const.EstimateRangeType.OPTIMISTIC);
            optimRange.setProjectId(projectId);
            AssembleUtil.assembleEstimateRange(optimRange, optimData);

            estimateRangeMapper.insert(pessiRange);
            estimateRangeMapper.insert(mostLikelyRange);
            estimateRangeMapper.insert(optimRange);
        }
    }

    /**
     * Get the total lines of code
     * of a specific project
     * @author Haoming Zhang
     * @date 4/6/19 20:37
     */
    @Override
    public Integer getTotalLinesByProjectId(Integer projectId) {
        return projectMapper.getTotalLinesByProjectId(projectId);
    }

    /**
     * Get the effort of code
     * of a specific project
     * @author Haoming Zhang
     * @date 4/6/19 20:42
     */
    @Override
    public Integer getEffortByProjectId(Integer projectId) {
        return projectMapper.getEffortByProjectId(projectId);
    }

}




















