package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * BO for Function Points Info
 * necessary for Module Size calculation
 * @author Oliver Day
 * @date 03/31/19
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FPInfoBO {
    private Integer language;

    private Integer manualMultiplier;

    private List<FPCountsBO> fpCountsBOList;


}
