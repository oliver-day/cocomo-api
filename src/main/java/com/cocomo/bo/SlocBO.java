package com.cocomo.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * SlocBO serves as the input when module size
 * needs to be updated by sloc methods
 * @author Oliver Day
 * @date 03/27/19
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SlocBO {
    private Integer newCodeSloc;

    private Integer adaptedCodeSloc;

    private Integer designModPerOfAsloc;

    private Integer codeModPerOfAsloc;

    private Integer integrationPerForAsloc;

    private Integer assessmentAssimilationRating;

    private Integer softwareUnderstandingRating;

    private Integer programmerUnfamiliarityLevel;

    private Integer automaticTranslationPer;
}
