package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Function Point Default Complexity Weights
 * @author Oliver Day
 * @date 03/29/19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FPDefault {

    private String functionType;

    private Integer low;

    private Integer average;

    private Integer high;
}
