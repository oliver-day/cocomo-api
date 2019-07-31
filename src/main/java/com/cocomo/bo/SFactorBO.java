package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Business object for scale factors
 * @author Haoming Zhang
 * @date 2/26/19 19:35
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SFactorBO {
    private Integer prec;

    private Integer flex;

    private Integer resl;

    private Integer team;

    private Integer pmat;
}
