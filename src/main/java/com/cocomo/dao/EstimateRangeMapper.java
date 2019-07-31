package com.cocomo.dao;

import com.cocomo.pojo.EstimateRange;
import org.apache.ibatis.annotations.Param;

public interface EstimateRangeMapper {

    int checkCalculated(@Param("projectId") Integer projectId);

    int deleteEstimatedRangeByProjectId(@Param("projectId") Integer projectId);

    int insert(EstimateRange estimateRange);

    int updateByPrimaryKey(EstimateRange estimateRange);

    EstimateRange getPessimisticByProjectId(@Param("projectId") Integer projectId);

    EstimateRange getMostLikelyByProjectId(@Param("projectId") Integer projectId);

    EstimateRange getOptimisticByProjectId(@Param("projectId") Integer projectId);
}
