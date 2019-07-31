package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Project details for
 * project level maintenance
 * @author Haoming Zhang
 * @date 4/6/19 18:17
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDetailsVO {

    private List<String> moduleNames;

    private Integer developmentSLOC;

    private Double personMonth;

    private Double developmentCost;
}
