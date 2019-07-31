package com.cocomo.vo;

import lombok.Data;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * VO class for testing purpose.
 * An view object contains properties that need to be returned to the client.
 * @author Haoming Zhang
 * @date 01/24/2019 19:28
 */
@Data // Consider using this notation instead of defining getter and setter explicitly.
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TestVO {
    private String name;
    private Integer age;
}
