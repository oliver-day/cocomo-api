package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * estimate_range
 * @author Haoming Zhang
 * @date 3/12/19 18:14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateRange {
    private Integer id;

    private Integer type;

    private Integer projectId;

    private Double effort;

    private Double sched;

    private Double prod;

    private Double cost;

    private Double inst;

    private Double staff;

    private Double risk;
}
