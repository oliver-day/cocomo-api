package com.cocomo.vo;

import com.cocomo.bo.EAFEarlyDesignBO;
import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * List View Object For EAFEarlyDesignDefaultVO
 * @author Dongchul Choi
 * @date 2019-03-24
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EAFEarlyDesignDefaultListVO {
    private List<EAFEarlyDesignDefaultVO> eafEarlyDesignDefaultVOList;

    public EAFEarlyDesignDefaultListVO() {
        eafEarlyDesignDefaultVOList = new ArrayList<EAFEarlyDesignDefaultVO>();
    }

    public void addEAFEarlyDesignDefaultVOToList(EAFEarlyDesignDefaultVO eafEarlyDesignDefaultVO) {
        eafEarlyDesignDefaultVOList.add(eafEarlyDesignDefaultVO);
    }
}
