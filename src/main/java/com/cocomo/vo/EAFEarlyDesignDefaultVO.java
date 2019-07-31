package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * View Object For EAFEarlyDesignDefault
 * @author Dongchul Choi
 * @date 2019-03-24
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class EAFEarlyDesignDefaultVO {
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
