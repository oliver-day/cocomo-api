package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import java.util.ArrayList;
import java.util.List;

/**
 * List View Object For EquationFactorDefaultVO
 * @author Dongchul Choi
 * @date 2019-03-24
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EquationFactorDefaultListVO {
    private List<EquationFactorDefaultVO> equationFactorDefaultVOList;

    public EquationFactorDefaultListVO() {
        equationFactorDefaultVOList = new ArrayList<EquationFactorDefaultVO>();
    }

    public void addEquationFactorDefaultVOToList(EquationFactorDefaultVO equationFactorDefaultVO) {
        equationFactorDefaultVOList.add(equationFactorDefaultVO);
    }
}
