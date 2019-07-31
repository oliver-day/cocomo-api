package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * View object for all
 * estimate ranges that
 * belongs to a project
 * @author Haoming Zhang
 * @date 3/12/19 21:14
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EstimateRangeListVO {
    private EstimateRangeVO pessimistic;

    private EstimateRangeVO mostLikely;

    private EstimateRangeVO optimistic;
}
