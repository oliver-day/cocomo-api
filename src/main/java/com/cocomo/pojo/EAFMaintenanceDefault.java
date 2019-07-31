package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * eaf_maintenance_default
 * @author Dongchul Choi
 * @date 4/22/19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EAFMaintenanceDefault {
    private Integer id;

    private String name;

    private Double vlow;

    private Double low;

    private Double nom;

    private Double high;

    private Double vhigh;

    private Double exhigh;
}
