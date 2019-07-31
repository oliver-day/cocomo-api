package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Business Object for maintenance
 * at module level
 * @author Haoming Zhang
 * @date 4/5/19 12:57
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ModuleMaintenanceBO {

    private EafBO eaf;

    private Double laborRate;

    private Double lifeSpan;

    private Double codeModification;

    private Double codeAdded;

    private Integer softwareUnderstanding;

    private Double unfamiliarityWithSoftware;
}
