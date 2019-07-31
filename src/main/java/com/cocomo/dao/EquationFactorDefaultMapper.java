package com.cocomo.dao;

import com.cocomo.pojo.EquationFactorDefault;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EquationFactorDefaultMapper {

    int checkEquationName(@Param("equationName") String equationName);

    int updateByUserInput(@Param("equationName") String equationName,
                          @Param("value") Double value);

    Double getDefaultValueById(@Param("id") Integer id);

    List<EquationFactorDefault> getEquationFactorDefault();
}
