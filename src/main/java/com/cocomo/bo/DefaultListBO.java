package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * BO for a list of default values
 * that needs to be changed.
 * @author Haoming Zhang
 * @date 3/5/19 19:07
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DefaultListBO {
    private List<DefaultBO> data;
}
