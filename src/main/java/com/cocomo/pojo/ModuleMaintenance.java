package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * module_maintenance
 * @author Haoming Zhang
 * @date 4/5/19 15:18
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleMaintenance {

    private Integer id;

    private Double pm;

    private Double cost;

    private Double percentageAdded;

    private Double percentageModified;

    private Double laborRate;

    private Integer softwareUnderstanding;

    private Double softwareUnfamiliarity;

    private Integer projectId;

    private Integer moduleIndex;

}
