package com.cocomo.common;

import java.text.DecimalFormat;

/**
 * Common Constant class.
 * @author Haoming Zhang
 * @date 01/24/2019 15:14
 */
public class Const {
    public static final Integer MAX_AGE = 100;

    public static DecimalFormat df2 = new DecimalFormat("#.##");

    private static double[] schedPercent = {0.75, 0.85, 1.00, 1.3, 1.6};

    private static double[] schedCoefficient = {1.50, 1.22, 1.00, 0.76, 0.63};

    public interface EstimateRangeType {
        Integer PESSIMISTIC = 1;
        Integer MOSTLIKELY = 2;
        Integer OPTIMISTIC = 3;
    }

    public interface ProjectStage {
        Integer EARLY_DESIGN = 1;
        Integer POST_ARCHITECTURE = 2;
    }

    public interface ErrorMessages {
        String INVALID_AGE = "INVALID_AGE";
        String INVALID_PROJECT_ID = "The project id does not exist.";
        String NOT_FOUND = "The resource you are looking for can not be found.";
        String INTERNAL_SERVER_ERROR = "Internal server error";
        String EMPTY_DRIVER_NAME = "The driver name is empty.";
        String EMPTY_DRIVER_VALUE = "The driver value is empty.";
        String INVALID_DRIVER_NAME = "The driver name is invalid: ";
        String INVALID_DRIVER_LEVEL = "The driver level is invalid.";
        String INVALID_DRIVER_VALUE = "Please enter a number between 0 and 10.";
        String INVALID_EQUATION_NAME = "The equation name is invalid: ";
        String INVALID_PUT = "Invalid input, please refer to the documentation.";
        String INVALID_STAGE = "The project stage is invalid.";
        String INVALID_MODULE_NUMBER = "Number of modules for a project should be less or equal to 10.";
        String INVALID_MODULE_INDEX = "The module index does not exist.";
        String NO_MODULE_PROJECT = "This project does not contain any module.";
        String EMOTY_SLOC_PARAMETER = "SLOC parameter missing.";
        String INVALID_SLOC_PARAMETER_VALUE = "Invalid SLOC parameter value provided.";
        String EMPTY_FUNCTION_POINT_TYPE = "A Function Point Type is empty.";
        String INVALID_FUNCTION_POINT_TYPE = "Invalid Function Point Type provided.";
        String INVALID_FUNCTION_POINT_TYPE_WEIGHT = "Invalid Function Point Type weight provided.";
        String EMPTY_FUNCTION_POINT_LANGUAGE = "Missing Function Point Language parameter.";
        String INVALID_FUNCTION_POINT_LANGUAGE = "Invalid Function Point Language indicator provided. Must be between -1 and 39.";
        String EMPTY_FUNCTION_POINT_MULT = "Missing Function Point Multiplier parameter.";
        String INVALID_FUNCITON_POINT_MULT = "Invalid Function Point Multiplier provided. Must be between 0 and 1000.";
        String INVALID_FUNCTION_POINT_TYPE_COUNT = "Invalid Function Point Type Count provided. " +
                                                   "Please provided a count between 0 and 1000.";
        String INVALID_MAINTENANCE_STAGE = "The project is not at post-architecture stage";
        String EMPTY_MODULE_MAINTENANCE_PARAM = "Missing MODULE MAINTENANCE parameter.";
        String RANGE_NOT_CALCULATED = "The estimate range has not been calculated";
        String INVALID_SOFTWARE_UNDERSTANDING = "Software Understanding should between 0 to 50.";
        String INVALID_SOFTWARE_UNFAMILIARITY = "Unfamiliarity with Software should between 0.0 to 1.0";
        String EMPTY_EAF_MAINTENANCE_VALUE = "The value for eaf maintenance drivers can not be empty";
        String MAINTENANCE_NOT_CALCULATED = "The maintenance data does not exist.";
    }

    public enum DriverLevel {
        VERY_LOW("vlow", 1),
        LOW("low", 2),
        NOMINAL("nom", 3),
        HIGH("high", 4),
        VERY_HIGH("vhigh", 5);

        private String value;
        private int index;

        DriverLevel(String value, int index) {
            this.value = value;
            this.index = index;
        }

        public static String getValue(int index) {
            for (DriverLevel driverLevel : DriverLevel.values()) {
                if (driverLevel.getIndex() == index) {
                    return driverLevel.getValue();
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum EAFDriverLevel {
        VERY_LOW("vlow", 1),
        LOW("low", 2),
        NOMINAL("nom", 3),
        HIGH("high", 4),
        VERY_HIGH("vhigh", 5),
        EXTRA_HIGH("exhigh", 6);

        private String value;
        private int index;

        EAFDriverLevel(String value, int index) {
            this.value = value;
            this.index = index;
        }

        public static String getValue(int index) {
            for (EAFDriverLevel eafDriverLevel : EAFDriverLevel.values()) {
                if (eafDriverLevel.getIndex() == index) {
                    return eafDriverLevel.getValue();
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }
    }

    public enum EAFEarlyDesignDriverLevel {
        EXTRA_LOW("exlow", 0),
        VERY_LOW("vlow", 1),
        LOW("low", 2),
        NOMINAL("nom", 3),
        HIGH("high", 4),
        VERY_HIGH("vhigh", 5),
        EXTRA_HIGH("exhigh", 6);

        private String value;
        private int index;

        EAFEarlyDesignDriverLevel(String value, int index) {
            this.value = value;
            this.index = index;
        }

        public static String getValue(int index) {
            for (EAFEarlyDesignDriverLevel eafEarlyDesignDriverLevel : EAFEarlyDesignDriverLevel.values()) {
                if (eafEarlyDesignDriverLevel.getIndex() == index) {
                    return eafEarlyDesignDriverLevel.getValue();
                }
            }
            return null;
        }

        public String getValue() {
            return value;
        }

        public int getIndex() {
            return index;
        }
    }

    /*!
         * 1. Function Description:
         *     Returns the percent requested by different effort level.
         *     It can be used to get the percent given level string.
         *
         * 2. Parameters:
         *     schedRating: the integer to deternmine the type of effort.
         *
         * 3. Creation Time and Owner: 02/13/2017; Zhibo Zhang
         *
         * Modified by Haoming Zhang
         * Time: 03/17/2019
         */

    public static Double getPercent(Integer schedRating) {
        Double res = 1.00;
        switch(schedRating) {
            case 1:
                res = schedPercent[0];
                break;
            case 2:
                res = schedPercent[1];
                break;
            case 3:
                res = schedPercent[2];
                break;
            case 4:
                res = schedPercent[3];
                break;
            case 5:
                res = schedPercent[4];
                break;
            default:
                res = 1.00;
                break;
        }
        return res;
    }

    /**
     * Get coefficient by rating
     * @author Haoming Zhang
     * @date 3/17/19 15:06
     */
    public static Double getCoefficient(Integer schedRating) {
        Double res = 1.00;
        switch(schedRating) {
            case 1:
                res = schedCoefficient[0];
                break;
            case 2:
                res = schedCoefficient[1];
                break;
            case 3:
                res = schedCoefficient[2];
                break;
            case 4:
                res = schedCoefficient[3];
                break;
            case 5:
                res = schedCoefficient[4];
                break;
            default:
                res = 1.00;
                break;
        }
        return res;
    }
}
