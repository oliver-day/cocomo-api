package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Sample POJO
 * @author Haoming Zhang
 * @date 2/11/19 21:33
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Integer id;

    private String name;

    private Integer age;
}
