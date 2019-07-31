package com.cocomo.service.Impl;

import com.cocomo.bo.ScheduleBO;
import com.cocomo.common.Const;
import com.cocomo.dao.EAFDefaultMapper;
import com.cocomo.dao.EAFEarlyDesignDefaultMapper;
import com.cocomo.pojo.EAFDefault;
import com.cocomo.service.ScheduleService;
import com.cocomo.util.DefaultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * The implementation of Schedule service
 * @author Haoming Zhang
 * @date 2/27/19 20:18
 */
@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private EAFEarlyDesignDefaultMapper eafEarlyDesignDefaultMapper;

    @Autowired
    private EAFDefaultMapper eafDefaultMapper;

    /**
     * Get the value for SCED according to
     * the user input and the project stage
     * @author Haoming Zhang
     * @date 2/27/19 19:07
     */
    @Override
    public Double getScheduleValue(ScheduleBO scheduleBO, Integer projectStage)
                                                            throws ClassNotFoundException {

        Double result = 0.0;

        if (projectStage.equals(Const.ProjectStage.EARLY_DESIGN)) {
            String scheduleLevel = Const.DriverLevel.getValue(scheduleBO.getSchedule());
            result = eafEarlyDesignDefaultMapper.selectScheduleDefaultByLevel(scheduleLevel);
        } else if (projectStage.equals(Const.ProjectStage.POST_ARCHITECTURE)) {
            String scheduleLevel = Const.DriverLevel.getValue(scheduleBO.getSchedule());
            result = eafDefaultMapper.selectScheduleDefaultByLevel(scheduleLevel);
        }

        return result;
    }

    /**
     * Get schedule default values
     * GET http://cocomo-api.com/projects/defaults/schedule
     * @author Dongchul Choi
     * @date 2019-03-24 17:39
     */
    @Override
    public EAFDefault getScheduleDefault(){ return eafDefaultMapper.getScheduleDefault(); }

}
