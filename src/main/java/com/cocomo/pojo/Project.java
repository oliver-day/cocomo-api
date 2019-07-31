package com.cocomo.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Project POJO
 * @author Haoming Zhang
 * @date 2/25/19 21:49
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    private Integer id;

    private Double scaleFactor;

    private Double schedule;

    private Integer scheduleLevel;

    private String notes;

    private Integer totalLines;

    private Integer effort;

    private Integer stage;
}
