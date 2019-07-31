package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * eaf_early_design
 * @author Haoming Zhang
 * @date 2/27/19 18:02
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EAFEarlyDesignDefault {
    private Integer id;

    private String name;

    private Double exlow;

    private Double vlow;

    private Double low;

    private Double nom;

    private Double high;

    private Double vhigh;

    private Double exhigh;
}
