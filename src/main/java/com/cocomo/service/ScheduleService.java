package com.cocomo.service;

import com.cocomo.bo.ScheduleBO;
import com.cocomo.pojo.EAFDefault;

/**
 * Schedule service
 * @author Haoming Zhang
 * @date 2/27/19 18:51
 */
public interface ScheduleService {

    /**
     * Get the value for SCED according to
     * the user input and the project stage
     * @author Haoming Zhang
     * @date 2/27/19 19:07
     */
    Double getScheduleValue(ScheduleBO scheduleBO, Integer projectStage) throws ClassNotFoundException;

    /**
     * Get schedule default values
     * GET http://cocomo-api.com/projects/defaults/schedule
     * @author Dongchul Choi
     * @date 2019-03-24 17:39
     */
    EAFDefault getScheduleDefault();

}
