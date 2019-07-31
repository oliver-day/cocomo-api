package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * List View Object For EAFDefaultVO
 * @author Dongchul Choi
 * @date 2019-04-22
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EAFMaintenanceDefaultListVO {
    private List<EAFMaintenanceDefaultVO> eafMaintenanceDefaultVOList;

    public EAFMaintenanceDefaultListVO() {
        eafMaintenanceDefaultVOList = new ArrayList<EAFMaintenanceDefaultVO>();
    }

    public void addEAFMaintenanceDefaultVOToList(EAFMaintenanceDefaultVO eafMaintenanceDefaultVO) {
        eafMaintenanceDefaultVOList.add(eafMaintenanceDefaultVO);
    }
}
