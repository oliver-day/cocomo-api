package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * EquationDefault List
 * @author Haoming Zhang
 * @date 3/13/19 18:12
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquationDefaultListBO {
    private List<EquationDefaultBO> data;
}
