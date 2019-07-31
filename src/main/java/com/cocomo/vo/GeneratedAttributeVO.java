package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * View object for generated
 * attributes like project id
 * and module index.
 * @author Haoming Zhang
 * @date 3/9/19 22:31
 */
@Data
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class GeneratedAttributeVO {
    private Integer id;
    private Integer moduleIndex;
}
