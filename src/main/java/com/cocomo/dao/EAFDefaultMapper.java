package com.cocomo.dao;

import com.cocomo.pojo.EAFDefault;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EAFDefaultMapper {

    int checkDriverName(@Param("driverName") String driverName);

    Double selectScheduleDefaultByLevel(@Param("scheduleLevel") String scheduleLevel);

    int updateByUserInput(@Param("driverName") String driverName,
                          @Param("driverLevel") String driverLevel,
                          @Param("value") Double value);

    List<Map<String,Object>> selectAllDefault();

    EAFDefault getScheduleDefault();

    List<EAFDefault> getEafDefault();
}
