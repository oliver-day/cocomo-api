package com.cocomo.dao;


import com.cocomo.pojo.EAFEarlyDesignDefault;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EAFEarlyDesignDefaultMapper {

    int checkDriverName(@Param("driverName") String driverName);

    Double selectScheduleDefaultByLevel(@Param("scheduleLevel") String scheduleLevel);

    int updateByUserInput(@Param("driverName") String driverName,
                          @Param("driverLevel") String driverLevel,
                          @Param("value") Double value);

    List<Map<String,Object>> selectAllDefault();

    List<EAFEarlyDesignDefault> getEafEarlyDesignDefault();
}
