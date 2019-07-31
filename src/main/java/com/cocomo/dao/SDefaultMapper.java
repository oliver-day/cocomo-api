package com.cocomo.dao;

import com.cocomo.pojo.SDefault;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SDefaultMapper {

    List<SDefault> selectAllSDefault();

    int updateByUserInput(@Param("driverName") String driverName,
                          @Param("driverLevel") String driverLevel,
                          @Param("value") Double value);

    int checkDriverName(@Param("driverName") String driverName);

    List<SDefault> getSdefaults();
}
