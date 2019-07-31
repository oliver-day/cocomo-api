package com.cocomo.vo;

import com.cocomo.vo.ModuleVO;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import java.util.ArrayList;
import java.util.List;

/**
 * View object for a List of ModuleVO
 * @author Oliver Day
 * @date 03/15/19
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ModuleListVO {
    private List<ModuleVO> moduleVOList;

    public ModuleListVO() {
        moduleVOList = new ArrayList<ModuleVO>();
    }

    public void addModuleVOToList(ModuleVO module) {
        moduleVOList.add(module);
    }
}
