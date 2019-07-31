package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VO class for estimated range
 * @author Haoming Zhang
 * @date 3/12/19 21:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateRangeVO {

    private Double effort;

    private Double sched;

    private Double prod;

    private Double cost;

    private Double inst;

    private Double staff;

    private Double risk;
}
