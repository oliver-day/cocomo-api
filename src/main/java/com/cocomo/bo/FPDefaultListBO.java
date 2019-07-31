package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * BO for a list of Function Point Complexity Weights
 * @author Oliver Day
 * @date 03/29/19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FPDefaultListBO {
    private List<FPDefaultBO> fpDefaultBOList;
}
