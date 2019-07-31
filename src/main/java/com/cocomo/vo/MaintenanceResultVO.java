package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * VO class for module
 * level maintenance result
 * @author Haoming Zhang
 * @date 4/5/19 19:12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceResultVO {

    private String moduleName;

    private Double maintenancePersonMonth;

    private Double maintenanceCost;
}
