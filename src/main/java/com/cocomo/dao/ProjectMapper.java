package com.cocomo.dao;

import com.cocomo.pojo.Project;
import org.apache.ibatis.annotations.Param;

public interface ProjectMapper {

    int insert(Project project);

    int deleteProjectByProjectId(@Param("projectId") Integer projectId);

    int checkProject(@Param("projectId") Integer projectId);

    Double getScaleFactorByProjectId(@Param("projectId") Integer projectId);

    int updateNotesByProjectId(@Param("projectId") Integer projectId, @Param("notes") String notes);

    Integer getStageByProjectId(@Param("projectId") Integer projectId);

    Double getScheduleByProjectId(@Param("projectId") Integer projectId);

    int updateScheduleLevelByProjectId(@Param("projectId") Integer projectId,
                                       @Param("scheduleLevel") Integer scheduleLevel);

    int updateScheduleValueByProjectId(@Param("projectId") Integer projectId,
                                       @Param("scheduleValue") Double scheduleValue);

    String getNotesByProjectId(@Param("projectId") Integer projectId);

    int updateScaleFactorByProjectId(@Param("projectId") Integer projectId, @Param("scaleFactor") Double scaleFactor);

    Integer getScheduleLevelByProjectId(@Param("projectId") Integer projectId);

    int updateTotalLinesByProjectId(@Param("projectId") Integer projectId, @Param("num") Integer num);

    int updateEffortByProjectId(@Param("projectId") Integer projectId, @Param("num") Integer num);

    Integer getTotalLinesByProjectId(@Param("projectId") Integer projectId);

    Integer getEffortByProjectId(@Param("projectId") Integer projectId);
}





















































