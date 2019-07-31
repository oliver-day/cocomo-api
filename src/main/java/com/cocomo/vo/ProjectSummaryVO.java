package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project summary for
 * project level maintenance
 * @author Haoming Zhang
 * @date 4/6/19 18:24
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectSummaryVO {

    private Double cumulativeMaintenancePM;

    private Double overallPM;

    private Double cumulativeMaintenanceCost;

    private Double overallCost;
}
