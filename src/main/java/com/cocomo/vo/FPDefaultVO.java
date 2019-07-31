package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * View Object For Function Point Complexity Weights
 * @author Oliver Day
 * @date 03/29/19
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class FPDefaultVO {

    private String functionType;

    private Integer low;

    private Integer average;

    private Integer high;
}
