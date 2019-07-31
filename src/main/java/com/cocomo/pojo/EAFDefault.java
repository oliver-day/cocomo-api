package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * eaf_default
 * @author Haoming Zhang
 * @date 3/4/19 19:25
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EAFDefault {
    private Integer id;

    private String name;

    private Double vlow;

    private Double low;

    private Double nom;

    private Double high;

    private Double vhigh;

    private Double exhigh;
}
