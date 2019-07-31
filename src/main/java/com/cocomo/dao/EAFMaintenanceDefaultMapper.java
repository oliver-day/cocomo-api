package com.cocomo.dao;

import com.cocomo.pojo.EAFMaintenanceDefault;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface EAFMaintenanceDefaultMapper {

    int checkDriverName(@Param("driverName") String driverName);

    int updateByUserInput(@Param("driverName") String driverName,
                          @Param("driverLevel") String driverLevel,
                          @Param("value") Double value);

    List<Map<String,Object>> selectAllDefault();

    List<EAFMaintenanceDefault> getEafMaintenanceDefault();
}
