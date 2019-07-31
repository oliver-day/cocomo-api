package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * List View Object For FPDefaultVO
 * @author Oliver Day
 * @date 03/29/19
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FPDefaultListVO {
    private List<FPDefaultVO> fpDefaultVOList;

    public FPDefaultListVO() {
        fpDefaultVOList = new ArrayList<FPDefaultVO>();
    }

    public void addPFDefaultVOToList(FPDefaultVO fpDefaultVO) {
        fpDefaultVOList.add(fpDefaultVO);
    }
}
