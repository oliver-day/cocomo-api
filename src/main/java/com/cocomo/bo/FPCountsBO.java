package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BO for Function Point Type Counts
 * @author Oliver Day
 * @date 03/31/19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FPCountsBO {
    private String functionType;

    private Integer low;

    private Integer average;

    private Integer high;
}
