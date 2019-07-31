package com.cocomo.service;

import com.cocomo.pojo.*;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
 * Project service
 * @author Haoming Zhang
 * @date 2/25/19 22:00
 */
public interface ProjectService {

    /**
     * Create a new project.
     * @author Haoming Zhang
     * @date 2/25/19 22:04
     */
    ResponseEntity createProject(Project project);

    /**
     * Get scheule of a specific project
     * @author Oliver Day
     * @data 03/06/19
     */
    Boolean deleteProjectByProjectId(Integer projectId);

    /**
     * Update project notes.
     * @author Haoming Zhang
     * @date 3/1/19 19:22
     */
    ResponseEntity updateProjectNotes(Integer projectId, String notes);

    /**
     * Get scheule of a specific project
     * @author Oliver Day
     * @data 03/06/19
     */
    Double getScheduleByProjectId(Integer projectId);

    /**
     * Update schedule of a
     * specific project
     * @author Oliver Day
     * @date 03/20/19
     */
    Boolean updateScheduleByProjectId(Integer projectId, Integer scheduleLevel, Double scheduleValue);

    /**
     * Check whether a project exists or not.
     * @author Haoming Zhang
     * @date 3/1/19 19:54
     */
    int checkProject(Integer projectId);

    /**
     * Get the scale factor of
     * a specific project.
     * @author Haoming Zhang
     * @date 2/27/19 21:15
     */
    Double getScaleFactorByProjectId(Integer projectId);

    /**
     * Get the stage of
     * a specific project.
     * @author Haoming Zhang
     * @date 3/9/19 18:15
     */
    Integer getStageByProjectId(Integer projectId);

    /**
     * Get the note of
     * a specific project.
     * GET http://cocomo-api.com/projects/{pid}/notes
     * @author Dongchul Choi
     * @date 03/05/19
     */
    String getNotesByProjectId(Integer projectId);

    /**
     * Update Scale Factor of a specific project
     * PUT http://cocomo-api.com/projects/{pid}/scale_factor
     * @author Dongchul Choi
     * @date 03/09/19
     */
     Integer updateProjectScaleFactor(Integer projectId, Double scaleFactor);

    /**
     * Delete the estimate ranges
     * of a specific project
     * @param projectId
     * @return
     * @author Oliver Day
     * @data 03/15/19
     */
     Boolean deleteEstimatedRangeByProjectId(Integer projectId);
     
     /**
      * Get the pessimistic
      * range of a specific
      * project
      * @author Haoming Zhang
      * @date 3/12/19 21:07
      */
     EstimateRange getPessimisticByProjectId(Integer projectId);

     /**
      * Get the most likely
      * range of a specific
      * project
      * @author Haoming Zhang
      * @date 3/12/19 21:35
      */
     EstimateRange getMostLikelyByProjectId(Integer projectId);

     /**
      * Get the optimistic
      * range of a specific
      * project
      * @author Haoming Zhang
      * @date 3/12/19 21:42
      */
     EstimateRange getOptimisticByProjectId(Integer projectId);

     /**
      * Check whether the estimate
      * range of a specific project
      * has been calculated or not
      * @author Haoming Zhang
      * @date 3/12/19 23:02
      */
     int checkCalculated(Integer projectId);

     /**
      * Calculate the estimate range
      * of a specific project as well
      * as certain columns of modules
      * that belong to this project.
      * @author Haoming Zhang
      * @date 3/16/19 22:29
      */
     void calculateEstimate(Integer projectId);

    /**
     * Set the estimate range of
     * a specific project
     * @author Haoming Zhang
     * @date 3/17/19 15:26
     */
    void setEstimateRange(Integer projectId, double[] rangeData,
                          Double C, Double F, Double totalSloc);

    /**
     * Get the total lines of code
     * of a specific project
     * @author Haoming Zhang
     * @date 4/6/19 20:37
     */
    Integer getTotalLinesByProjectId(Integer projectId);

    /**
     * Get the effort of code
     * of a specific project
     * @author Haoming Zhang
     * @date 4/6/19 20:42
     */
    Integer getEffortByProjectId(Integer projectId);

}
