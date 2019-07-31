package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * list View object for SDefaultVO
 * @author Dongchul Choi
 * @date 03/24/19
 */

@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class SDefaultListVO {
    private List<SDefaultVO> sDefaultVOList;

    public SDefaultListVO() {
        sDefaultVOList = new ArrayList<SDefaultVO>();
    }

    public void addSDefaultVOToList(SDefaultVO sDefault) {
        sDefaultVOList.add(sDefault);
    }
}
