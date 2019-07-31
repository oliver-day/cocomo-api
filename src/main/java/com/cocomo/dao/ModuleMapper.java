package com.cocomo.dao;

import com.cocomo.pojo.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ModuleMapper {

    int insert(Module module);

    int getTotalModuleByProjectId(@Param("projectId") Integer projectId);

    List<String> getModuleNamesByProjectId(@Param("projectId") Integer projectId);

    int checkModule(@Param("projectId") Integer projectId,
                    @Param("moduleIndex") Integer moduleIndex);

    int deleteModulesByProjectId(@Param("projectId") Integer projectId);

    int deleteModuleByProjectIdAndModuleIndex(@Param("projectId") Integer projectId,
                                              @Param("moduleIndex") Integer moduleIndex);

    int updateModuleIndex(@Param("projectId") Integer projectId,
                          @Param("moduleIndex") Integer moduleIndex);

    Module getModuleRowByProjectId(@Param("projectId") Integer projectId,
                                   @Param("moduleIndex") Integer moduleIndex);

    List<Module> getModulesByProjectId(@Param("projectId") Integer projectId);


    String getModuleNameByProjectIdAndModuleIndex(@Param("projectId") Integer projectId,
                                                  @Param("moduleIndex") Integer moduleIndex);

    Double getAggregateSloc(@Param("projectId") Integer projectId);

    int updateCalculatedColumnsByPrimaryKey(Module module);

    int updateModuleEaf(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex, @Param("eaf") Double eaf);

    int updateModulesEAfDueToScheduleChange(@Param("projectId") Integer projectId, @Param("oldSCHEDValue") Double oldSCHEDValue,
                                            @Param("newSCHEDValue") Double newSCHEDValue);

    int updateModuleName(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex, @Param("name") String name);

    int updateModuleLabor(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex, @Param("labor") Double labor);

    int updateModuleSize(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex,
                         @Param("moduleSize") Integer moduleSize);

    int getModuleSize(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex);

    Double getModuleEAF(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex);

    String getModuleLanguage(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex);

    Double getCostByProjectAndModuleIndex(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex);

    Double getNomEffortByProjectAndModuleIndex(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex);

    Double getModuleLabor(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex);

    int updateModuleLanguage(@Param("projectId") Integer projectId, @Param("moduleIndex") Integer moduleIndex,
                         @Param("moduleLanguage") String moduleLanguage);
}
