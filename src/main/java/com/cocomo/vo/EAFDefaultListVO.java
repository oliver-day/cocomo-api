package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * List View Object For EAFDefaultVO
 * @author Dongchul Choi
 * @date 2019-03-24
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EAFDefaultListVO {
    private List<EAFDefaultVO> eafDefaultVOList;

    public EAFDefaultListVO() {
        eafDefaultVOList = new ArrayList<EAFDefaultVO>();
    }

    public void addEAFDefaultVOToList(EAFDefaultVO eafDefaultVO) {
        eafDefaultVOList.add(eafDefaultVO);
    }
}
