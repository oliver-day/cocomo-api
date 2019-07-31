package com.cocomo.controller;

import com.cocomo.bo.*;
import com.cocomo.common.Const;
import com.cocomo.pojo.*;
import com.cocomo.service.*;
import com.cocomo.vo.*;
import com.cocomo.util.ParamUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import java.util.Map;


/**
 * Project controller
 * @author Haoming Zhang
 * @date 2/25/19 21:33
 */
@Controller
@RequestMapping("projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ScaleFactorService scaleFactorService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private EAFService eafService;

    @Autowired
    private MaintenanceService maintenanceService;

    /**
     * Create a new project.
     * @author Haoming Zhang
     * @date 2/26/19 23:17
     */
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseEntity createProject(@RequestBody Project project) throws ClassNotFoundException,
                                                                             NoSuchMethodException,
                                                                             IllegalAccessException,
                                                                             InvocationTargetException {
        if (null == project.getScaleFactor()) {
            SFactorBO sFactorBO = new SFactorBO();
            sFactorBO.setFlex(Const.DriverLevel.NOMINAL.getIndex());
            sFactorBO.setPmat(Const.DriverLevel.NOMINAL.getIndex());
            sFactorBO.setPrec(Const.DriverLevel.NOMINAL.getIndex());
            sFactorBO.setResl(Const.DriverLevel.NOMINAL.getIndex());
            sFactorBO.setTeam(Const.DriverLevel.NOMINAL.getIndex());
            Double defaultSF = scaleFactorService.calculateScaleFactor(sFactorBO);
            project.setScaleFactor(defaultSF);
        }

        if (null == project.getStage()) {
            project.setStage(Const.ProjectStage.EARLY_DESIGN);
        } else if (!project.getStage().equals(Const.ProjectStage.EARLY_DESIGN)
                && !project.getStage().equals(Const.ProjectStage.POST_ARCHITECTURE)) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_STAGE, HttpStatus.BAD_REQUEST);
        }

        if (null == project.getTotalLines()) {
            project.setTotalLines(0);
        }

        if (null == project.getEffort()) {
            project.setEffort(0);
        }

        if (null == project.getSchedule()) {
            ScheduleBO scheduleBO = new ScheduleBO();
            scheduleBO.setSchedule(Const.DriverLevel.NOMINAL.getIndex());
            Double scheduleValue = scheduleService.getScheduleValue(scheduleBO, project.getStage());
            project.setSchedule(scheduleValue);
            project.setScheduleLevel(Const.DriverLevel.NOMINAL.getIndex());
        }

        return projectService.createProject(project);
    }

    /**
     * Delete all info related
     * to project
     * with projectId
     * @param projectId
     * @author Oliver Day
     */
    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProject(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that projedct exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        Boolean projectInfoDeleted = projectService.deleteProjectByProjectId(projectId);
        if(!projectInfoDeleted) {
            return new ResponseEntity<>("Failure, the project with project id = "
                    + projectId + " was not deleted. Error with project table operation.",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }

        int count1 = moduleService.getTotalModuleByProjectId(projectId);
        if (count1 > 1) {
            Boolean moduleInfoDeleted = moduleService.deleteModulesByProjectId(projectId);
            if(!moduleInfoDeleted) {
                return new ResponseEntity<>("Failure, the project with project id = "
                        + projectId + " was not deleted. Error with module table operation.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        int count2 = projectService.checkCalculated(projectId);
        if (count2 > 0) {
            Boolean estimatedRangeDeleted = projectService.deleteEstimatedRangeByProjectId(projectId);
            if(!estimatedRangeDeleted) {
                return new ResponseEntity<>("Failure, the project with project id = "
                        + projectId + " was not deleted. Error with estimate_range table operation.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Success, the project with project id = "
                + projectId + " was deleted", HttpStatus.OK);
    }

    /**
     * Get the scale factor of
     * a specific project.
     * @author Haoming Zhang
     * @date 2/27/19 20:58
     */
    @RequestMapping(value = "/{projectId}/scale_factor", method = RequestMethod.GET)
    public ResponseEntity getName(@PathVariable(value = "projectId") Integer projectId) {
        Double scaleFactor = projectService.getScaleFactorByProjectId(projectId);
        if (scaleFactor == null) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }
        ScaleFactorVO scaleFactorVO = new ScaleFactorVO();
        scaleFactorVO.setScaleFactor(scaleFactor);

        return new ResponseEntity<>(scaleFactorVO, HttpStatus.OK);
    }

    /**
     * Update the notes of the
     * given project.
     * @author Haoming Zhang
     * @date 3/1/19 18:19
     */
    @RequestMapping(value = "/{projectId}/notes", method = RequestMethod.PUT)
    public ResponseEntity updateProjectNotes(@RequestBody NotesBO notesBO,
                                             @PathVariable(value = "projectId") Integer projectId) {
        int count = projectService.checkProject(projectId);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }
        return projectService.updateProjectNotes(projectId, notesBO.getNotes());
    }

    /**
     * Get the schedule of
     * a specific project.
     * @author Oliver Day
     * @date 03/06/19
     */
    @RequestMapping(value = "/{projectId}/schedule", method = RequestMethod.GET)
    public ResponseEntity getProjectSchedule(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        Double schedule = projectService.getScheduleByProjectId(projectId);
        ScheduleVO scheduleVO = new ScheduleVO();
        scheduleVO.setSchedule(schedule);

        return new ResponseEntity<>(scheduleVO, HttpStatus.OK);
    }

    /**
     * Update schedule of
     * a specific project
     * @author Oliver Day
     * @date 03/20/19
     */
    @RequestMapping(value = "/{projectId}/schedule", method = RequestMethod.PUT)
    public ResponseEntity updateProjectSchedule(@RequestBody ScheduleBO scheduleBO,
                                                @PathVariable(value = "projectId") Integer projectId)
                                                                            throws ClassNotFoundException{
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        if(!ParamUtil.rangeInDefined(scheduleBO.getSchedule(), 1, 5)) {
            return new ResponseEntity<>("Invalid Schedule rating provided. Please provide a rating in range of 1-5.", HttpStatus.BAD_REQUEST);
        }

        Double oldSCHEDValue = projectService.getScheduleByProjectId(projectId);

        Integer projectStage = projectService.getStageByProjectId(projectId);
        Integer newScheduleLevel = scheduleBO.getSchedule();
        Double newSCEDValue = scheduleService.getScheduleValue(scheduleBO, projectStage);

        Boolean projectScheduleUpdated = projectService.updateScheduleByProjectId(projectId, newScheduleLevel, newSCEDValue);
        if(!projectScheduleUpdated) {
            return new ResponseEntity<>("Failed to update schedule for Project table.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        Boolean moduleEafUpdated = moduleService.updateEAFOfModulesDueToScheduleChange(projectId, oldSCHEDValue, newSCEDValue);
        if(!moduleEafUpdated) {
            return new ResponseEntity<>("Failed to update schedule for Module table.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Successfully updated Project's Schedule Rating.", HttpStatus.OK);
    }

    /**
     * Add a module to a
     * specific project.
     * @author Haoming Zhang
     * @date 3/8/19 23:02
     */
    @RequestMapping(value = "/{projectId}/modules", method = RequestMethod.POST)
    public ResponseEntity createModule(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        Module module = new Module();

        // Verify the total number does not exceed the limit
        int modCount = moduleService.getTotalModuleByProjectId(projectId);
        if (modCount >= 10) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_NUMBER, HttpStatus.BAD_REQUEST);
        } else {
            // Set default value
            module.setModuleIndex(modCount + 1);

            String newModuleName = "Module" + String.valueOf(modCount + 1);
            boolean suitable = false;
            List<String> moduleNames = moduleService.getModuleNamesByProjectId(projectId);
            // When adding new module with duplicate name, add ' to the new name
            while(!suitable){
                boolean duplicate = false;
                for(String moduleName: moduleNames) {
                    if(moduleName.equals(newModuleName)) {
                        newModuleName = newModuleName + "'";
                        duplicate = true;
                        break;
                    }
                }
                if(!duplicate){
                    suitable = true;
                }
            }
            module.setName(newModuleName);

            module.setSize(0);
            module.setLabor(0.0);

            // Calculate eaf
            Integer stage = projectService.getStageByProjectId(projectId);
            if (stage.equals(Const.ProjectStage.EARLY_DESIGN)) {
                EAFEarlyDesignBO eafEarlyDesignBO = new EAFEarlyDesignBO();
                eafEarlyDesignBO.setFcil(Const.EAFEarlyDesignDriverLevel.NOMINAL.getIndex());
                eafEarlyDesignBO.setPdif(Const.EAFEarlyDesignDriverLevel.NOMINAL.getIndex());
                eafEarlyDesignBO.setPers(Const.EAFEarlyDesignDriverLevel.NOMINAL.getIndex());
                eafEarlyDesignBO.setPrex(Const.EAFEarlyDesignDriverLevel.NOMINAL.getIndex());
                eafEarlyDesignBO.setRcpx(Const.EAFEarlyDesignDriverLevel.NOMINAL.getIndex());
                eafEarlyDesignBO.setRuse(Const.EAFEarlyDesignDriverLevel.NOMINAL.getIndex());
                Double earlyDesignEAF = eafService.calculateEarlyDesignEAF(eafEarlyDesignBO);
                Double schedule = projectService.getScheduleByProjectId(projectId);
                module.setEaf(Double.valueOf(Const.df2.format(earlyDesignEAF * schedule)));
            } else if (stage.equals(Const.ProjectStage.POST_ARCHITECTURE)) {
                EafBO eafBO = new EafBO();
                eafBO.setAcap(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setApex(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setCplx(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setData(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setDocu(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setLtex(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setPcap(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setPcon(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setPlex(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setPvol(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setRely(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setRuse(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setSite(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setStor(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setTime(Const.EAFDriverLevel.NOMINAL.getIndex());
                eafBO.setTool(Const.EAFDriverLevel.NOMINAL.getIndex());
                Double eaf = eafService.calculateEAF(eafBO);
                Double schedule = projectService.getScheduleByProjectId(projectId);
                module.setEaf(Double.valueOf(Const.df2.format(eaf * schedule)));
            }

            module.setLanguage("Non-Specified");
            module.setNomEffortDev(0.0);
            module.setEstEffortDev(0.0);
            module.setProd(0.0);
            module.setCost(0.0);
            module.setInstCost(0.0);
            module.setStaff(0.0);
            module.setRisk(0.0);
            module.setProjectId(projectId);
        }
        Boolean isSuccess = moduleService.createModule(module);
        if (isSuccess) {
            GeneratedAttributeVO generatedAttributeVO = new GeneratedAttributeVO();
            generatedAttributeVO.setModuleIndex(module.getModuleIndex());
            return new ResponseEntity<>(generatedAttributeVO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to insert a new module",
                                            HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /**
     * Get the note of a specific project
     * GET http://cocomo-api.com/projects/{pid}/notes
     * @author Dongchul Choi
     * @date 03/11/19
     */
    @RequestMapping(value = "/{projectId}/notes", method = RequestMethod.GET)
    public ResponseEntity getProjectNotes(@PathVariable(value = "projectId") Integer projectId) {
        // If there is no corresponding projectId
        int count = projectService.checkProject(projectId);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }
        String notes = projectService.getNotesByProjectId(projectId);
        NotesVO notesVO = new NotesVO();
        notesVO.setNotes(notes);

        return new ResponseEntity<>(notesVO, HttpStatus.OK);
    }

    /**
     * Update Scale Factor of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/scale_factor
     * @author Dongchul Choi
     * @date 03/11/19
     */
    @RequestMapping(value = "/{projectId}/scale_factor", method = RequestMethod.PUT)
    public ResponseEntity updateProjectScaleFactor( @PathVariable(value = "projectId") Integer projectId,
                                                    @RequestBody SFactorBO sFactorBO ) throws ClassNotFoundException,
                                                                                            NoSuchMethodException,
                                                                                            InvocationTargetException,
                                                                                            IllegalAccessException {
        // If there is no corresponding projectId
        int count = projectService.checkProject(projectId);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // If the value of one of the Drivers is missing
        if (sFactorBO.getFlex()==null || sFactorBO.getTeam()==null || sFactorBO.getResl()==null || sFactorBO.getPmat()==null || sFactorBO.getPrec()==null){
            return new ResponseEntity<>(Const.ErrorMessages.EMPTY_DRIVER_VALUE, HttpStatus.BAD_REQUEST);
        }

        // If the scope of Driver value is not valid
        int min = Const.DriverLevel.VERY_LOW.getIndex();
        int max = Const.DriverLevel.VERY_HIGH.getIndex();
        if (!ParamUtil.rangeInDefined(sFactorBO.getFlex(),min,max) ||
                !ParamUtil.rangeInDefined(sFactorBO.getPmat(),min,max) ||
                !ParamUtil.rangeInDefined(sFactorBO.getPrec(),min,max) ||
                !ParamUtil.rangeInDefined(sFactorBO.getResl(),min,max) ||
                !ParamUtil.rangeInDefined(sFactorBO.getTeam(),min,max)){
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
        }

        // Calculate new scale factor value
        Double scaleFactor = scaleFactorService.calculateScaleFactor(sFactorBO);
        count = projectService.updateProjectScaleFactor(projectId, scaleFactor);

        if (count > 0) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Remove a module by project
     * id and module index
     * @author Haoming Zhang
     * @date 3/11/19 23:04
     */
    @RequestMapping(value = "/{projectId}/modules/{moduleIndex}", method = RequestMethod.DELETE)
    public ResponseEntity deleteModule(@PathVariable(value = "projectId") Integer projectId,
                                       @PathVariable(value = "moduleIndex") Integer moduleIndex) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        // Delete a module and update module_index
        Boolean isSuccess = moduleService.deleteModule(projectId, moduleIndex);

        if (isSuccess){
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }

        return new ResponseEntity<>("Failed to update.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get the module name of
     * a specific module
     * @author Haoming Zhang
     * @date 3/12/19 14:23
     */
    @RequestMapping(value = "/{projectId}/modules/{moduleIndex}/module_name", method = RequestMethod.GET)
    public ResponseEntity getModuleName(@PathVariable(value = "projectId") Integer projectId,
                                        @PathVariable(value = "moduleIndex") Integer moduleIndex) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        String name = moduleService.getModuleNameByProjectIdAndModuleIndex(projectId, moduleIndex);

        ModuleVO moduleVO = new ModuleVO();
        moduleVO.setName(name);

        return new ResponseEntity<>(moduleVO, HttpStatus.OK);
    }

    /**
     * Get details of a specific module in a project by project id and module index
     * GET http://cocomo-api.com/projects/{projectId}/modules/{moduleIndex}
     * @author Dongchul Choi
     * @date 03/12/19
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}", method = RequestMethod.GET)
    public ResponseEntity getModuleRowByProjectId(@PathVariable(value = "projectId") Integer projectId, @PathVariable(value = "moduleIndex") Integer moduleIndex) {
        // If there is no corresponding projectId
        int count = projectService.checkProject(projectId);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // If there is no corresponding moduleIndex
        count = moduleService.checkModule(projectId, moduleIndex);
        if (count > 0) {
            ModuleVO moduleVO = new ModuleVO();
            Module module = moduleService.getModuleRowByProjectId(projectId, moduleIndex);
            BeanUtils.copyProperties(module, moduleVO);
            return new ResponseEntity<>(moduleVO, HttpStatus.OK);
        }
        return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
    }

    /**
     * Get all modules belong to a project with projectId
     * @param projectId
     * @return
     * @authoer Oliver Day
     * @date 03/15/19
     */
    @RequestMapping(value = "/{projectId}/modules", method = RequestMethod.GET)
    public ResponseEntity getModulesByProjectId(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        int moduleCountWithProjectId = moduleService.getTotalModuleByProjectId(projectId);
        if(moduleCountWithProjectId < 1) {
            return new ResponseEntity<>("No modules to return.", HttpStatus.OK);
        }

        List<Module> modulesWithProjectId = moduleService.getModulesByProjectId(projectId);
        ModuleListVO moduleVOList = new ModuleListVO();

        for(Module module : modulesWithProjectId){
            ModuleVO moduleVOInstance = new ModuleVO();
            BeanUtils.copyProperties(module, moduleVOInstance);
            moduleVOList.addModuleVOToList(moduleVOInstance);
        }

        return new ResponseEntity<>(moduleVOList, HttpStatus.OK);
    }

    /**
     * Get the estimated range
     * of a specific project
     * @author Haoming Zhang
     * @date 3/12/19 17:39
     */
    @RequestMapping(value="/{projectId}/estimates", method = RequestMethod.GET)
    public ResponseEntity getEstimateRange(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        EstimateRangeListVO estimateRangeListVO = new EstimateRangeListVO();

        // Verify that the estimate value has been calculated
        int countCalculated = projectService.checkCalculated(projectId);
        if(countCalculated == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.RANGE_NOT_CALCULATED, HttpStatus.BAD_REQUEST);
        }

        EstimateRange pessimisticRange = projectService.getPessimisticByProjectId(projectId);
        EstimateRangeVO pessimisticRangeVO = new EstimateRangeVO();
        BeanUtils.copyProperties(pessimisticRange, pessimisticRangeVO);

        EstimateRange mostLikelyRange = projectService.getMostLikelyByProjectId(projectId);
        EstimateRangeVO mostLikelyRangeVO = new EstimateRangeVO();
        BeanUtils.copyProperties(mostLikelyRange, mostLikelyRangeVO);

        EstimateRange optimisticRange = projectService.getOptimisticByProjectId(projectId);
        EstimateRangeVO optimisticRangeVO = new EstimateRangeVO();
        BeanUtils.copyProperties(optimisticRange, optimisticRangeVO);

        estimateRangeListVO.setMostLikely(mostLikelyRangeVO);
        estimateRangeListVO.setPessimistic(pessimisticRangeVO);
        estimateRangeListVO.setOptimistic(optimisticRangeVO);

        return new ResponseEntity<>(estimateRangeListVO, HttpStatus.OK);
    }

    /**
     * Calculate the estimate range
     * of a specific project as well
     * as certain columns of modules
     * that belong to this project.
     * @author Haoming Zhang
     * @date 3/17/19 17:53
     */
    @RequestMapping(value="/{projectId}/calculation", method = RequestMethod.GET)
    public ResponseEntity performCalculation(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }
        // Verify that there exist modules to calculate
        int moduleCountWithProjectId = moduleService.getTotalModuleByProjectId(projectId);
        if(moduleCountWithProjectId < 1) {
            return new ResponseEntity<>(Const.ErrorMessages.NO_MODULE_PROJECT, HttpStatus.BAD_REQUEST);
        }

        projectService.calculateEstimate(projectId);

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Update EAF of specific module by project Id and module index
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/eaf
     * @author Dongchul Choi
     * @date 03/19/19
     * Notice that there are 2 stages, and the cost drivers are different.
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/eaf", method = RequestMethod.PUT)
    public ResponseEntity updateModuleEaf(@PathVariable(value = "projectId") Integer projectId, @PathVariable(value = "moduleIndex") Integer moduleIndex,
                                          @RequestBody Map<String, Object> param) throws IllegalArgumentException, NullPointerException{

        // If there is no corresponding projectId
        int count = projectService.checkProject(projectId);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // If there is no corresponding moduleIndex
        count = moduleService.checkModule(projectId, moduleIndex);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        Double calculateEAF = 0.00;
        Integer stage = projectService.getStageByProjectId(projectId);
        ObjectMapper mapper = new ObjectMapper();
        if (stage.equals(Const.ProjectStage.EARLY_DESIGN)) {
            EAFEarlyDesignBO eafEarlyDesignBO = mapper.convertValue(param, EAFEarlyDesignBO.class);
            // If the scope of Driver value is not valid
            if (
                !ParamUtil.rangeInDefined(eafEarlyDesignBO.getPers(), Const.EAFEarlyDesignDriverLevel.EXTRA_LOW.getIndex(), Const.EAFEarlyDesignDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafEarlyDesignBO.getRcpx(), Const.EAFEarlyDesignDriverLevel.EXTRA_LOW.getIndex(), Const.EAFEarlyDesignDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafEarlyDesignBO.getPdif(), Const.EAFEarlyDesignDriverLevel.LOW.getIndex(), Const.EAFEarlyDesignDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafEarlyDesignBO.getPrex(), Const.EAFEarlyDesignDriverLevel.EXTRA_LOW.getIndex(), Const.EAFEarlyDesignDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafEarlyDesignBO.getFcil(), Const.EAFEarlyDesignDriverLevel.EXTRA_LOW.getIndex(), Const.EAFEarlyDesignDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafEarlyDesignBO.getRuse(), Const.EAFEarlyDesignDriverLevel.LOW.getIndex(), Const.EAFEarlyDesignDriverLevel.EXTRA_HIGH.getIndex())
            ) {
                    return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
            }
            calculateEAF = eafService.calculateEarlyDesignEAF(eafEarlyDesignBO);
        } else if (stage.equals(Const.ProjectStage.POST_ARCHITECTURE)) {
            EafBO eafBO = mapper.convertValue(param, EafBO.class);
            if (
                !ParamUtil.rangeInDefined(eafBO.getRely(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getData(), Const.EAFDriverLevel.LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getDocu(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getCplx(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getRuse(), Const.EAFDriverLevel.LOW.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getTime(), Const.EAFDriverLevel.NOMINAL.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getStor(), Const.EAFDriverLevel.NOMINAL.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getPvol(), Const.EAFDriverLevel.LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getAcap(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getPcap(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getPcon(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getApex(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getLtex(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getPlex(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getTool(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
                !ParamUtil.rangeInDefined(eafBO.getSite(), Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex())
            ) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
            }
            calculateEAF = eafService.calculateEAF(eafBO);
        }
        Double schedule = projectService.getScheduleByProjectId(projectId);
        Double eaf = Double.valueOf(Const.df2.format(calculateEAF * schedule));
        count = moduleService.updateModuleEaf(projectId, moduleIndex, eaf);

        if (count > 0) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update.", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /**
     * Update Module Name of a specific module of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/module_name
     * @author Dongchul Choi
     * @date 2019-03-26
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/module_name", method = RequestMethod.PUT)
    public ResponseEntity updateModuleEaf(@PathVariable(value = "projectId") Integer projectId, @PathVariable(value = "moduleIndex") Integer moduleIndex,
                                          @RequestBody NameBO nameBO){

        // If there is no corresponding projectId
        int count = projectService.checkProject(projectId);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // If there is no corresponding moduleIndex
        count = moduleService.checkModule(projectId, moduleIndex);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        count = moduleService.updateModuleName(projectId, moduleIndex, nameBO.getName());
        if (count > 0) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Update labor of a specific module of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/modules/{mid}/labor
     * @author Dongchul Choi
     * @date 2019-03-26
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/labor", method = RequestMethod.PUT)
    public ResponseEntity updateModuleEaf(@PathVariable(value = "projectId") Integer projectId, @PathVariable(value = "moduleIndex") Integer moduleIndex,
                                          @RequestBody LaborBO laborBO){

        // If there is no corresponding projectId
        int count = projectService.checkProject(projectId);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // If there is no corresponding moduleIndex
        count = moduleService.checkModule(projectId, moduleIndex);
        if (count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        count = moduleService.updateModuleLabor(projectId, moduleIndex, Double.valueOf(Const.df2.format(laborBO.getLabor())));
        if (count > 0) {
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Update the module size for a given module and a given project
     * Module size is determined from calculations on new sloc and adapted sloc
     * @author Oliver Day
     * @date 03/27/19
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/module_size/sloc", method = RequestMethod.PUT)
    public ResponseEntity updateModuleSizeBySLOC(@PathVariable(value = "projectId") Integer projectId,
                                                 @PathVariable(value = "moduleIndex") Integer moduleIndex,
                                                 @RequestBody SlocBO slocBO) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        // Verify slocBO parameters
        if(slocBO.getNewCodeSloc()==null || slocBO.getAdaptedCodeSloc()==null ||
           slocBO.getDesignModPerOfAsloc()==null || slocBO.getCodeModPerOfAsloc()==null ||
           slocBO.getIntegrationPerForAsloc()==null || slocBO.getAssessmentAssimilationRating()==null ||
           slocBO.getSoftwareUnderstandingRating()==null || slocBO.getProgrammerUnfamiliarityLevel()==null ||
           slocBO.getAutomaticTranslationPer()==null) {
            return new ResponseEntity<>(Const.ErrorMessages.EMOTY_SLOC_PARAMETER, HttpStatus.BAD_REQUEST);
        }

        if(
                !ParamUtil.rangeInDefined(slocBO.getNewCodeSloc(), 0, 9999999) ||
                !ParamUtil.rangeInDefined(slocBO.getAdaptedCodeSloc(), 0, 9999999) ||
                !ParamUtil.rangeInDefined(slocBO.getDesignModPerOfAsloc(), 0, 100) ||
                        !ParamUtil.rangeInDefined(slocBO.getCodeModPerOfAsloc(), 0, 100) ||
                        !ParamUtil.rangeInDefined(slocBO.getIntegrationPerForAsloc(), 0, 100) ||
                        !ParamUtil.rangeInDefined(slocBO.getAssessmentAssimilationRating(), 0, 10) ||
                        !ParamUtil.rangeInDefined(slocBO.getSoftwareUnderstandingRating(), 1, 5) ||
                        !ParamUtil.rangeInDefined(slocBO.getProgrammerUnfamiliarityLevel(), 0, 10) ||
                        !ParamUtil.rangeInDefined(slocBO.getAutomaticTranslationPer(), 0, 100)


        ) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_SLOC_PARAMETER_VALUE, HttpStatus.BAD_REQUEST);
        }

        // Calculate equivalent SLOC
        int equivalentSLOC = moduleService.calculateModuleSizeBySLOC(slocBO);

        // Verify that calculated equivalentSLOC is appropriate
        if(equivalentSLOC < 2000) {
            return new ResponseEntity<>("COCOMO calibration has been done for SLOC above 2000. " +
                                               "You have tried to set the equivalent SLOC to " + equivalentSLOC +
                                                ". Please adjust SLOC parameters.", HttpStatus.OK);
        }

        if(equivalentSLOC > 9999999) {
            return new ResponseEntity<>("Calculated equivalent SLOC is above 9,999,999. Please adjust SLOC paramters",
                    HttpStatus.OK);
        }

        int updated_count = moduleService.updateModuleSize(projectId, moduleIndex, equivalentSLOC);
        if(updated_count > 0) {
            return new ResponseEntity<>("Successfully updated module size.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update module size.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Update the module size for a given module and a given project
     * Module size is determined from calculations on Function Points Counts
     * @author Oliver Day
     * @date 03/31/19
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/module_size/function_points", method = RequestMethod.PUT)
    public ResponseEntity updateModuleSizeByFunctionPoints(@PathVariable(value = "projectId") Integer projectId,
                                                 @PathVariable(value = "moduleIndex") Integer moduleIndex,
                                                 @RequestBody FPInfoBO fpInfoBO) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        // Verify fpInfoBO parameters
        if(fpInfoBO.getLanguage() == null) {
            return new ResponseEntity<>(Const.ErrorMessages.EMPTY_FUNCTION_POINT_LANGUAGE, HttpStatus.BAD_REQUEST);
        }

        if(!ParamUtil.rangeInDefined(fpInfoBO.getLanguage(), -1, 39)){
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_FUNCTION_POINT_LANGUAGE, HttpStatus.BAD_REQUEST);
        }

        if(fpInfoBO.getManualMultiplier() == null) {
            return new ResponseEntity<>(Const.ErrorMessages.EMPTY_FUNCTION_POINT_MULT, HttpStatus.BAD_REQUEST);
        }

        if(!ParamUtil.rangeInDefined(fpInfoBO.getManualMultiplier(), 0, 1000)) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_FUNCITON_POINT_MULT, HttpStatus.BAD_REQUEST);
        }

        for(FPCountsBO fpCountsBO : fpInfoBO.getFpCountsBOList()) {
            if(!ParamUtil.rangeInDefined(fpCountsBO.getLow(), 0, 1000) ||
               !ParamUtil.rangeInDefined(fpCountsBO.getAverage(), 0, 1000) ||
               !ParamUtil.rangeInDefined(fpCountsBO.getHigh(), 0, 1000)) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_FUNCTION_POINT_TYPE_COUNT, HttpStatus.BAD_REQUEST);
            }
        }

        // Calculate equivalent SLOC
        int equivalentSLOC = moduleService.calculateModuleSizeByFunctionPoints(fpInfoBO);

        // Verify that calculated equivalentSLOC is appropriate
        if(equivalentSLOC < 2000) {
            return new ResponseEntity<>("COCOMO calibration has been done for SLOC above 2000. " +
                    "You have tried to set the equivalent SLOC to " + equivalentSLOC +
                    ". Please adjust Function Points Count parameters.", HttpStatus.OK);
        }

        if(equivalentSLOC > 9999999) {
            return new ResponseEntity<>("Calculated equivalent SLOC is above 9,999,999. Please adjust Function Point Count paramters",
                    HttpStatus.OK);
        }

        int updated_count = moduleService.updateModuleSize(projectId, moduleIndex, equivalentSLOC);
        if(updated_count > 0) {
            return new ResponseEntity<>("Successfully updated module size.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update module size.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get the module size for a given module and a given project
     * @author Oliver Day
     * @date 03/28/19
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/module_size", method = RequestMethod.GET)
    public ResponseEntity getModuleSize(@PathVariable(value = "projectId") Integer projectId,
                                        @PathVariable(value = "moduleIndex") Integer moduleIndex) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        int moduleSize = moduleService.getModuleSize(projectId, moduleIndex);
        ModuleSizeVO moduleSizeVO = new ModuleSizeVO();
        moduleSizeVO.setModuleSize(moduleSize);

        return new ResponseEntity<>(moduleSizeVO, HttpStatus.OK);
    }

    /**
     * Get the module eaf for a given module and a given project
     * @author Oliver Day
     * @date 03/28/19
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/eaf", method = RequestMethod.GET)
    public ResponseEntity getModuleEAF(@PathVariable(value = "projectId") Integer projectId,
                                        @PathVariable(value = "moduleIndex") Integer moduleIndex) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        Double moduleEAF = moduleService.getModuleEAF(projectId, moduleIndex);
        ModuleEAFVO moduleEAFVO = new ModuleEAFVO();
        moduleEAFVO.setModuleEAF(moduleEAF);

        return new ResponseEntity<>(moduleEAFVO, HttpStatus.OK);
    }

    /**
     * Get the module programming language for a given module and a given project
     * @author Oliver Day
     * @date 03/28/19
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/language", method = RequestMethod.GET)
    public ResponseEntity getModuleLanguage(@PathVariable(value = "projectId") Integer projectId,
                                       @PathVariable(value = "moduleIndex") Integer moduleIndex) {

        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        String moduleLanguage = moduleService.getModuleLanguage(projectId, moduleIndex);
        ModuleLanguageVO moduleLanguageVO = new ModuleLanguageVO();
        moduleLanguageVO.setModuleLanguage(moduleLanguage);

        return new ResponseEntity<>(moduleLanguageVO, HttpStatus.OK);
    }

    /**
     * Get the module labor costs for a given module and a given project
     * @author Oliver Day
     * @date 03/28/19
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/labor", method = RequestMethod.GET)
    public ResponseEntity getModuleLabor(@PathVariable(value = "projectId") Integer projectId,
                                            @PathVariable(value = "moduleIndex") Integer moduleIndex) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        Double moduleLabor = moduleService.getModuleLabor(projectId, moduleIndex);
        ModuleLaborVO moduleLaborVO = new ModuleLaborVO();
        moduleLaborVO.setModuleLabor(moduleLabor);

        return new ResponseEntity<>(moduleLaborVO, HttpStatus.OK);
    }

    /**
     * Update the programming language for a given module and a given project
     * @author Oliver Day
     * @date 03/28/19
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/language", method = RequestMethod.PUT)
    public ResponseEntity updateModuleLanguage(@PathVariable(value = "projectId") Integer projectId,
                                            @PathVariable(value = "moduleIndex") Integer moduleIndex,
                                            @RequestBody ModuleLanguageBO moduleLanguageBO) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        int updatedCount = moduleService.updateModuleLanguage(projectId, moduleIndex, moduleLanguageBO.getModuleLanguage());
        if (updatedCount > 0) {
            return new ResponseEntity<>("Successfully updated module language.", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update module language.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get the stage of a specific
     * project.
     * @author Haoming Zhang
     * @date 3/29/19 21:06
     */
    @RequestMapping(value="/{projectId}/stage", method = RequestMethod.GET)
    public ResponseEntity getProjectStage(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        Integer stage = projectService.getStageByProjectId(projectId);
        StageVO stageVO = new StageVO();
        stageVO.setStage(stage);

        return new ResponseEntity<>(stageVO, HttpStatus.OK);
    }

    /**
     * Calculate maintenance
     * for a specific module
     * @author Haoming Zhang
     * @date 4/5/19 13:30
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/maintenance", method = RequestMethod.PUT)
    public ResponseEntity calculateModuleMaintenance(@PathVariable(value = "projectId") Integer projectId,
                                                     @PathVariable(value = "moduleIndex") Integer moduleIndex,
                                                     @RequestBody ModuleMaintenanceBO moduleMaintenanceBO) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID, HttpStatus.BAD_REQUEST);
        }

        // Verify that project is at post-architecture stage
        Integer stage = projectService.getStageByProjectId(projectId);
        if (!stage.equals(Const.ProjectStage.POST_ARCHITECTURE)) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MAINTENANCE_STAGE, HttpStatus.BAD_REQUEST);
        }

        // Verify that module exists
        int moduleCount = moduleService.checkModule(projectId, moduleIndex);
        if(moduleCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MODULE_INDEX, HttpStatus.BAD_REQUEST);
        }

        // Verify moduleMaintenanceBO
        if (moduleMaintenanceBO.getEaf() == null || moduleMaintenanceBO.getCodeAdded() == null
            || moduleMaintenanceBO.getLifeSpan() == null || moduleMaintenanceBO.getLaborRate() == null
            || moduleMaintenanceBO.getLaborRate() == null || moduleMaintenanceBO.getCodeModification() == null) {
            return new ResponseEntity<>(Const.ErrorMessages.EMPTY_MODULE_MAINTENANCE_PARAM,
                                        HttpStatus.BAD_REQUEST);
        }
        if (moduleMaintenanceBO.getSoftwareUnderstanding() == null ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getSoftwareUnderstanding(), 0, 50)) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_SOFTWARE_UNDERSTANDING,
                                        HttpStatus.BAD_REQUEST);
        }
        if (moduleMaintenanceBO.getUnfamiliarityWithSoftware() == null ||
            moduleMaintenanceBO.getUnfamiliarityWithSoftware() < 0.0 ||
            moduleMaintenanceBO.getUnfamiliarityWithSoftware() > 1.0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_SOFTWARE_UNFAMILIARITY,
                                        HttpStatus.BAD_REQUEST);
        }
        // Verify eaf drivers for maintenance
        if (moduleMaintenanceBO.getEaf().getRely() == null || moduleMaintenanceBO.getEaf().getData() == null ||
            moduleMaintenanceBO.getEaf().getDocu() == null || moduleMaintenanceBO.getEaf().getCplx() == null ||
            moduleMaintenanceBO.getEaf().getRuse() == null || moduleMaintenanceBO.getEaf().getTime() == null ||
            moduleMaintenanceBO.getEaf().getStor() == null || moduleMaintenanceBO.getEaf().getPvol() == null ||
            moduleMaintenanceBO.getEaf().getAcap() == null || moduleMaintenanceBO.getEaf().getPcap() == null ||
            moduleMaintenanceBO.getEaf().getPcon() == null || moduleMaintenanceBO.getEaf().getApex() == null ||
            moduleMaintenanceBO.getEaf().getLtex() == null || moduleMaintenanceBO.getEaf().getPlex() == null ||
            moduleMaintenanceBO.getEaf().getTool() == null || moduleMaintenanceBO.getEaf().getSite() == null) {
            return new ResponseEntity<>(Const.ErrorMessages.EMPTY_EAF_MAINTENANCE_VALUE,
                                        HttpStatus.BAD_REQUEST);
        }
        if (!ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getRely(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getData(),
                Const.EAFDriverLevel.LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getDocu(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getCplx(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getRuse(),
                Const.EAFDriverLevel.NOMINAL.getIndex(), Const.EAFDriverLevel.NOMINAL.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getTime(),
                Const.EAFDriverLevel.NOMINAL.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getStor(),
                Const.EAFDriverLevel.NOMINAL.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getPvol(),
                Const.EAFDriverLevel.LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getAcap(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getPcap(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getPcon(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getApex(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getLtex(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getPlex(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getTool(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.VERY_HIGH.getIndex()) ||
            !ParamUtil.rangeInDefined(moduleMaintenanceBO.getEaf().getSite(),
                Const.EAFDriverLevel.VERY_LOW.getIndex(), Const.EAFDriverLevel.EXTRA_HIGH.getIndex())) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
        }

        // Calculate development cost if it has not been calculated yet
        if (projectService.checkCalculated(projectId) == 0) {
            projectService.calculateEstimate(projectId);
        }

        //Calculate maintenance eaf
        Double maintenanceEAF = eafService.calculateMaintenanceEAF(moduleMaintenanceBO.getEaf());

        Boolean isSuccess = maintenanceService.calculateModuleMaintenance(
                                                            maintenanceEAF,
                                                            moduleMaintenanceBO.getLaborRate(),
                                                            moduleMaintenanceBO.getLifeSpan(),
                                                            moduleMaintenanceBO.getCodeModification(),
                                                            moduleMaintenanceBO.getCodeAdded(),
                                                            moduleMaintenanceBO.getSoftwareUnderstanding(),
                                                            moduleMaintenanceBO.getUnfamiliarityWithSoftware(),
                                                            projectId, moduleIndex);

        if (isSuccess){
            return new ResponseEntity<>("Success", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to calculate.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Get the result of module
     * level maintenance
     * @author Haoming Zhang
     * @date 4/5/19 19:03
     */
    @RequestMapping(value="/{projectId}/modules/{moduleIndex}/maintenance", method = RequestMethod.GET)
    public ResponseEntity getModuleMaintenance(@PathVariable(value = "projectId") Integer projectId,
                                                     @PathVariable(value = "moduleIndex") Integer moduleIndex) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID,
                                        HttpStatus.BAD_REQUEST);
        }

        // Verify that project is at post-architecture stage
        Integer stage = projectService.getStageByProjectId(projectId);
        if (!stage.equals(Const.ProjectStage.POST_ARCHITECTURE)) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MAINTENANCE_STAGE,
                                        HttpStatus.BAD_REQUEST);
        }

        // Verify the result has been calculated
        int countMaintenance = maintenanceService.checkCalculated(projectId, moduleIndex);
        if(countMaintenance == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.MAINTENANCE_NOT_CALCULATED,
                                        HttpStatus.BAD_REQUEST);
        }

        String moduleName = moduleService.getModuleNameByProjectIdAndModuleIndex(projectId, moduleIndex);
        int moduleSize = moduleService.getModuleSize(projectId, moduleIndex);
        Double moduleCost = moduleService.getModuleCost(projectId, moduleIndex);
        Double moduleNominalPM = moduleService.getModuleNominalPM(projectId, moduleIndex);

        ModuleMaintenance maintenance = maintenanceService.getMaintenanceByProjectAndModuleIndex(projectId,
                                                                                                 moduleIndex);

        // Assemble result
        ModuleResultVO moduleResultVO = new ModuleResultVO();
        moduleResultVO.setModuleName(moduleName);
        moduleResultVO.setDevelopmentSLOC(moduleSize);
        moduleResultVO.setDevelopmentNominalPersonMonth(moduleNominalPM);
        moduleResultVO.setDevelopmentCost(moduleCost);
        moduleResultVO.setMaintenanceLaborRate(maintenance.getLaborRate());
        moduleResultVO.setPercentageAdded(maintenance.getPercentageAdded());
        moduleResultVO.setPercentageModified(maintenance.getPercentageModified());
        moduleResultVO.setMaintenanceSoftwareUnderstanding(maintenance.getSoftwareUnderstanding());
        moduleResultVO.setUnfamiliarityWithSoftware(maintenance.getSoftwareUnfamiliarity());
        moduleResultVO.setChangeTraffic(maintenance.getPercentageAdded() + maintenance.getPercentageModified());

        MaintenanceResultVO maintenanceResultVO = new MaintenanceResultVO();
        maintenanceResultVO.setModuleName(moduleName);
        maintenanceResultVO.setMaintenancePersonMonth(maintenance.getPm());
        maintenanceResultVO.setMaintenanceCost(maintenance.getCost());

        ModuleMaintenanceVO moduleMaintenanceVO = new ModuleMaintenanceVO();
        moduleMaintenanceVO.setMaintenanceResult(maintenanceResultVO);
        moduleMaintenanceVO.setModuleResult(moduleResultVO);

        return new ResponseEntity<>(moduleMaintenanceVO, HttpStatus.OK);
    }

    /**
     * Get the result of project
     * level maintenance
     * @author Haoming Zhang
     * @date 4/6/19 17:55
     */
    @RequestMapping(value="/{projectId}/maintenance", method = RequestMethod.GET)
    public ResponseEntity getProjectMaintenance(@PathVariable(value = "projectId") Integer projectId) {

        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID,
                    HttpStatus.BAD_REQUEST);
        }

        // Verify that project is at post-architecture stage
        Integer stage = projectService.getStageByProjectId(projectId);
        if (!stage.equals(Const.ProjectStage.POST_ARCHITECTURE)) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_MAINTENANCE_STAGE,
                    HttpStatus.BAD_REQUEST);
        }

        // Verify that project has at least one module
        int totalCount = moduleService.getTotalModuleByProjectId(projectId);
        if(totalCount == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.NO_MODULE_PROJECT,
                    HttpStatus.BAD_REQUEST);
        }

        ProjectMaintenanceVO projectMaintenanceVO = maintenanceService.assembleProjectMaintenance(projectId);

        return new ResponseEntity<>(projectMaintenanceVO, HttpStatus.OK);
    }

    /**
     * Get the total lines of a project
     * @author Haoming Zhang
     * @date 4/6/19 20:33
     */
    @RequestMapping(value="/{projectId}/total_lines", method = RequestMethod.GET)
    public ResponseEntity getProjectTotalLines(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID,
                    HttpStatus.BAD_REQUEST);
        }

        Integer lines = projectService.getTotalLinesByProjectId(projectId);
        ProjectCostVO projectCostVO = new ProjectCostVO();
        projectCostVO.setTotalLinesOfCode(lines);

        return new ResponseEntity<>(projectCostVO, HttpStatus.OK);
    }

    /**
     * Get the effort of a project
     * @author Haoming Zhang
     * @date 4/6/19 20:48
     */
    @RequestMapping(value="/{projectId}/effort", method = RequestMethod.GET)
    public ResponseEntity getProjectEffort(@PathVariable(value = "projectId") Integer projectId) {
        // Verify that project exists
        int count = projectService.checkProject(projectId);
        if(count == 0) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_PROJECT_ID,
                    HttpStatus.BAD_REQUEST);
        }

        Integer effort = projectService.getEffortByProjectId(projectId);
        ProjectCostVO projectCostVO = new ProjectCostVO();
        projectCostVO.setEffort(effort);

        return new ResponseEntity<>(projectCostVO, HttpStatus.OK);
    }
}
