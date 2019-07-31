package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * View object for SDefault
 * @author Dongchul Choi
 * @date 03/24/19
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SDefaultVO {
    private Integer id;

    private String name;

    private Double vlow;

    private Double low;

    private Double nom;

    private Double high;

    private Double vhigh;
}
