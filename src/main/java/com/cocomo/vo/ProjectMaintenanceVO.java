package com.cocomo.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Maintenance for project level
 * @author Haoming Zhang
 * @date 4/6/19 18:29
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectMaintenanceVO {

    private ProjectDetailsVO projectDetails;

    private ProjectSummaryVO summaryOfProject;
}
