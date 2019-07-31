package com.cocomo.vo;

import com.cocomo.pojo.Module;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * View object for Module
 * @author Dongchul Choi
 * @date 03/12/19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ModuleVO {
    private Integer id;

    private Integer moduleIndex;

    private String name;

    private Integer size;

    private Double labor;

    private Double eaf;

    private String language;

    private Double nomEffortDev;

    private Double estEffortDev;

    private Double prod;

    private Double cost;

    private Double instCost;

    private Double staff;

    private Double risk;

    private Integer projectId;
}
