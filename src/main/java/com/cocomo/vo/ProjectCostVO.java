package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * VO for total lines of
 * code and effort
 * @author Haoming Zhang
 * @date 4/6/19 20:44
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProjectCostVO {

    private Integer totalLinesOfCode;

    private Integer effort;
}
