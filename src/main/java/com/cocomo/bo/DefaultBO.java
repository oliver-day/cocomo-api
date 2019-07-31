package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Business object for defaults.
 * @author Haoming Zhang
 * @date 3/1/19 13:21
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultBO {
    private String driverName;

    private Integer driverLevel;

    private Double value;
}
