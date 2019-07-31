package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VO class for module result
 * @author Haoming Zhang
 * @date 4/5/19 19:12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleResultVO {

    private String moduleName;

    private Integer developmentSLOC;

    private Double developmentNominalPersonMonth;

    private Double developmentCost;

    private Double maintenanceLaborRate;

    private Double percentageAdded;

    private Double percentageModified;

    private Integer maintenanceSoftwareUnderstanding;

    private Double unfamiliarityWithSoftware;

    private Double changeTraffic;
}
