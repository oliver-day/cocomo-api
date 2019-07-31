package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * BO class for updating
 * equation defaults
 * @author Haoming Zhang
 * @date 3/13/19 16:43
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquationDefaultBO {
    private String equationName;

    private Double value;
}
