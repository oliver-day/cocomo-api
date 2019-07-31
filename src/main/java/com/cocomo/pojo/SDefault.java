package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * scalefactor_default
 * @author Haoming Zhang
 * @date 2/26/19 16:44
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SDefault {

    private Integer id;

    private String name;

    private Double vlow;

    private Double low;

    private Double nom;

    private Double high;

    private Double vhigh;
}
