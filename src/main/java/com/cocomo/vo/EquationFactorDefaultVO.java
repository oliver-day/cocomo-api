package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * View Object For EquationFactorDefault
 * @author Dongchul Choi
 * @date 2019-03-24
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EquationFactorDefaultVO {
    private Integer id;

    private String name;

    private Double value;
}
