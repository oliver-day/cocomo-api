package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Module POJO
 * @author Haoming Zhang
 * @date 3/8/19 20:09
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Module {
    private Integer id;

    private Integer moduleIndex;

    private String name;

    private Integer size;

    private Double labor;

    private Double eaf;

    private String language;

    private Double nomEffortDev;

    private Double estEffortDev;

    private Double prod;

    private Double cost;

    private Double instCost;

    private Double staff;

    private Double risk;

    private Integer projectId;

}
