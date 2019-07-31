package com.cocomo.controller;

import com.cocomo.bo.DefaultBO;
import com.cocomo.bo.DefaultListBO;
import com.cocomo.bo.EquationDefaultBO;
import com.cocomo.bo.EquationDefaultListBO;
import com.cocomo.bo.FPDefaultBO;
import com.cocomo.bo.FPDefaultListBO;
import com.cocomo.common.Const;
import com.cocomo.pojo.*;
import com.cocomo.service.*;
import com.cocomo.util.ParamUtil;
import com.cocomo.vo.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * Default value Controller
 * @author Haoming Zhang
 * @date 2/28/19 22:27
 */
@Controller
@RequestMapping("defaults")
public class DefaultController {

    @Autowired
    private ScaleFactorService scaleFactorService;

    @Autowired
    private EAFService eafService;

    @Autowired
    private EquationService equationService;

    @Autowired
    private FunctionPointService functionPointService;

    @Autowired
    private ScheduleService scheduleService;

    /**
     * Update scale factor.
     * @author Haoming Zhang
     * @date 2/28/19 22:35
     */
    @RequestMapping(value = "/scale_factor", method = RequestMethod.PUT)
    public ResponseEntity updateScaleFactor(@RequestBody DefaultListBO defaultListBO) {
        List<DefaultBO> scaleFactorList = defaultListBO.getData();
        // Input verification
        for (DefaultBO defaultBO : scaleFactorList) {
            if (defaultBO.getDriverName() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.EMPTY_DRIVER_NAME, HttpStatus.BAD_REQUEST);
            }
            int count = scaleFactorService.checkDriverName(defaultBO.getDriverName());
            if (count == 0) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_NAME
                                            + defaultBO.getDriverName(),
                                            HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getDriverLevel() == null ||
                    !ParamUtil.rangeInDefined(defaultBO.getDriverLevel(),
                            Const.DriverLevel.VERY_LOW.getIndex(),
                            Const.DriverLevel.VERY_HIGH.getIndex())) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getValue() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_VALUE, HttpStatus.BAD_REQUEST);
            }
        }

        for (DefaultBO defaultBO : scaleFactorList) {
            Boolean isSuccess = scaleFactorService.updateScaleFactor(defaultBO);
            if (!isSuccess) {
                return new ResponseEntity<>("Failed to update driver "
                        + defaultBO.getDriverName() + ", please re-update every driver.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Modify eaf default.
     * @author Haoming Zhang
     * @date 3/4/19 19:17
     */
    @RequestMapping(value = "/eaf", method = RequestMethod.PUT)
    public ResponseEntity updateEAF(@RequestBody DefaultListBO defaultListBO) {
        List<DefaultBO> defaultBOList = defaultListBO.getData();
        // Input verification
        for (DefaultBO defaultBO : defaultBOList) {
            if (defaultBO.getDriverName() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.EMPTY_DRIVER_NAME, HttpStatus.BAD_REQUEST);
            }
            int count = eafService.checkDriverName(defaultBO.getDriverName());
            if (count == 0) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_NAME
                                        + defaultBO.getDriverName(), HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getDriverLevel() == null ||
                    !ParamUtil.rangeInDefined(defaultBO.getDriverLevel(),
                            Const.EAFDriverLevel.VERY_LOW.getIndex(),
                            Const.EAFDriverLevel.EXTRA_HIGH.getIndex())) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getValue() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_VALUE, HttpStatus.BAD_REQUEST);
            }
        }

        // Update default value
        for (DefaultBO defaultBO : defaultBOList) {
            Boolean isSuccess = eafService.updateEAFDefault(defaultBO);
            if (!isSuccess) {
                return new ResponseEntity<>("Failed to update driver "
                        + defaultBO.getDriverName() + ", please re-update every driver.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Modify eaf_early_design default.
     * @author Haoming Zhang
     * @date 3/8/19 19:28
     */
    @RequestMapping(value = "/eaf_early_design", method = RequestMethod.PUT)
    public ResponseEntity updateEAFEarlyDesign(@RequestBody DefaultListBO defaultListBO) {
        List<DefaultBO> defaultBOList = defaultListBO.getData();
        // Input verification
        for (DefaultBO defaultBO : defaultBOList) {
            if (defaultBO.getDriverName() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.EMPTY_DRIVER_NAME, HttpStatus.BAD_REQUEST);
            }
            int count = eafService.checkDriverNameForEarlyDesign(defaultBO.getDriverName());
            if (count == 0) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_NAME
                        + defaultBO.getDriverName(), HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getDriverLevel() == null ||
                    !ParamUtil.rangeInDefined(defaultBO.getDriverLevel(),
                            Const.EAFEarlyDesignDriverLevel.EXTRA_LOW.getIndex(),
                            Const.EAFEarlyDesignDriverLevel.EXTRA_HIGH.getIndex())) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getValue() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_VALUE, HttpStatus.BAD_REQUEST);
            }
        }

        // Update default value
        for (DefaultBO defaultBO : defaultBOList) {
            Boolean isSuccess = eafService.updateEAFEarlyDesignDefault(defaultBO);
            if (!isSuccess) {
                return new ResponseEntity<>("Failed to update driver "
                        + defaultBO.getDriverName() + ", please re-update every driver.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Modify the default value
     * for sced.
     * @author Haoming Zhang
     * @date 3/29/19 22:12
     */
    @RequestMapping(value = "/schedule", method = RequestMethod.PUT)
    public ResponseEntity updateEAFMaintenance(@RequestBody DefaultBO defaultBO) {
        defaultBO.setDriverName("sced");

        // Input verification
        if (defaultBO.getDriverLevel() == null ||
                !ParamUtil.rangeInDefined(defaultBO.getDriverLevel(),
                        Const.DriverLevel.VERY_LOW.getIndex(),
                        Const.DriverLevel.VERY_HIGH.getIndex())) {
            return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
        }

        Boolean isSuccess = eafService.updateScheduleDefault(defaultBO);
        if (!isSuccess) {
            return new ResponseEntity<>("Failed to update default schedule value",
                                        HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Modify eaf_maintenance default.
     * @author Haoming Zhang
     * @date 3/13/19 15:18
     */
    @RequestMapping(value = "/eaf_maintenance", method = RequestMethod.PUT)
    public ResponseEntity updateEAFMaintenance(@RequestBody DefaultListBO defaultListBO) {
        List<DefaultBO> defaultBOList = defaultListBO.getData();
        // Input verification
        for (DefaultBO defaultBO : defaultBOList) {
            if (defaultBO.getDriverName() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.EMPTY_DRIVER_NAME, HttpStatus.BAD_REQUEST);
            }
            int count = eafService.checkDriverNameForMaintenance(defaultBO.getDriverName());
            if (count == 0) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_NAME
                        + defaultBO.getDriverName(), HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getDriverLevel() == null ||
                    !ParamUtil.rangeInDefined(defaultBO.getDriverLevel(),
                            Const.EAFDriverLevel.VERY_LOW.getIndex(),
                            Const.EAFDriverLevel.EXTRA_HIGH.getIndex())) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_LEVEL, HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getValue() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_VALUE, HttpStatus.BAD_REQUEST);
            }
        }

        // Update default value
        for (DefaultBO defaultBO : defaultBOList) {
            Boolean isSuccess = eafService.updateMaintenanceDefault(defaultBO);
            if (!isSuccess) {
                return new ResponseEntity<>("Failed to update driver "
                        + defaultBO.getDriverName() + ", please re-update every driver.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Modify default value
     * for equations
     * @author Haoming Zhang
     * @date 3/13/19 17:56
     */
    @RequestMapping(value = "/equation", method = RequestMethod.PUT)
    public ResponseEntity updateEquation(@RequestBody EquationDefaultListBO equationDefaultListBO) {
        List<EquationDefaultBO> defaultBOList = equationDefaultListBO.getData();
        // Input verification
        for (EquationDefaultBO defaultBO : defaultBOList) {
            if (defaultBO.getEquationName() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.EMPTY_DRIVER_NAME, HttpStatus.BAD_REQUEST);
            }
            int count = equationService.checkEquation(defaultBO.getEquationName());
            if (count == 0) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_EQUATION_NAME
                        + defaultBO.getEquationName(), HttpStatus.BAD_REQUEST);
            }
            if (defaultBO.getValue() == null || defaultBO.getValue() < 0 || defaultBO.getValue() > 10) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_DRIVER_VALUE, HttpStatus.BAD_REQUEST);
            }
        }

        // Update default value
        for (EquationDefaultBO defaultBO : defaultBOList) {
            Boolean isSuccess = equationService.updateEquationDefault(defaultBO);
            if (!isSuccess) {
                return new ResponseEntity<>("Failed to update driver "
                        + defaultBO.getEquationName() + ", please re-update every driver.",
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    /**
     * Get the default function point complexity weights
     * @authoer Oliver Day
     * @date 03/29/19
     */
    @RequestMapping(value="/function_points", method = RequestMethod.GET)
    public ResponseEntity getDefaultFunctionPoints() {

        List<FPDefault> fpDefaults = functionPointService.getDefaultFunctionPoints();
        FPDefaultListVO fpDefaultListVO = new FPDefaultListVO();
        for(FPDefault fpDefault : fpDefaults) {
            FPDefaultVO fpDefaultVO = new FPDefaultVO();
            BeanUtils.copyProperties(fpDefault, fpDefaultVO);
            fpDefaultListVO.addPFDefaultVOToList(fpDefaultVO);
        }

        return new ResponseEntity<>(fpDefaultListVO, HttpStatus.OK);
    }

    /**
     * Update the default function point complexity weights
     * @authoer Oliver Day
     * @date 03/30/19
     */
    @RequestMapping(value="/function_points", method = RequestMethod.PUT)
    public ResponseEntity updateDefaultFunctionPoints(@RequestBody FPDefaultListBO fpDefaultListBO) {

        List<FPDefaultBO> functionPointList = fpDefaultListBO.getFpDefaultBOList();

        // Verify BO Input
        for(FPDefaultBO fpDefaultBO : functionPointList) {
            if(fpDefaultBO.getFunctionType() == null) {
                return new ResponseEntity<>(Const.ErrorMessages.EMPTY_FUNCTION_POINT_TYPE, HttpStatus.BAD_REQUEST);
            }

            int validFunctionTypeCount = functionPointService.checkFunctionPointType(fpDefaultBO.getFunctionType());
            if(validFunctionTypeCount == 0) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_FUNCTION_POINT_TYPE +
                                                  fpDefaultBO.getFunctionType(), HttpStatus.BAD_REQUEST);
            }

            if(fpDefaultBO.getLow() == null || !ParamUtil.rangeInDefined(fpDefaultBO.getLow(), 0, 25) ||
               fpDefaultBO.getAverage() == null || !ParamUtil.rangeInDefined(fpDefaultBO.getAverage(), 0, 25) ||
               fpDefaultBO.getHigh() == null || !ParamUtil.rangeInDefined(fpDefaultBO.getHigh(), 0, 25)) {
                return new ResponseEntity<>(Const.ErrorMessages.INVALID_FUNCTION_POINT_TYPE_WEIGHT, HttpStatus.BAD_REQUEST);
            }
        }

        // Update Default Function Point Complexity Weights
        for(FPDefaultBO fpDefaultBO : functionPointList) {
            Boolean successfullyUpdated = functionPointService.updateDefaultFunctionPoints(fpDefaultBO);
            if(!successfullyUpdated) {
                return new ResponseEntity<>("Failed to update Function Point Type Weights" +
                                            fpDefaultBO.getFunctionType() + ", please re-update Function Type Weights.",
                                            HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("Successfully updated Default Function Point Complexity Weights.", HttpStatus.OK);
    }

    /**
     * Get Scale factors default values
     * @author Dongchul Choi
     * @date 03/24/19
     */
    @RequestMapping(value="/scale_factor", method = RequestMethod.GET)
    public ResponseEntity getDefaultScalefactor() {
        List<SDefault> sDefaults = scaleFactorService.getSdefaults();
        SDefaultListVO sDefaultListVO = new SDefaultListVO();
        for (SDefault sDefault : sDefaults){
            SDefaultVO sDefaultVO = new SDefaultVO();
            BeanUtils.copyProperties(sDefault,sDefaultVO);
            sDefaultListVO.addSDefaultVOToList(sDefaultVO);
        }
        return new ResponseEntity<>(sDefaultListVO, HttpStatus.OK);
    }

    /**
     * Get schedule default values
     * @author Dongchul Choi
     * @date 2019-03-24 17:39
     */
    @RequestMapping(value="/schedule", method = RequestMethod.GET)
    public ResponseEntity getDefaultSchedule() {
        EAFDefaultVO eafDefaultVO = new EAFDefaultVO();
        BeanUtils.copyProperties(scheduleService.getScheduleDefault(),eafDefaultVO);

        return new ResponseEntity<>(eafDefaultVO, HttpStatus.OK);
    }

    /**
     * Get eaf default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    @RequestMapping(value="/eaf", method = RequestMethod.GET)
    public ResponseEntity getDefaultEaf() {
        List<EAFDefault> eafDefaults = eafService.getEafDefault();
        EAFDefaultListVO eafDefaultListVO = new EAFDefaultListVO();
        for (EAFDefault eafDefault : eafDefaults){
            EAFDefaultVO eafDefaultVO = new EAFDefaultVO();
            BeanUtils.copyProperties(eafDefault,eafDefaultVO);
            eafDefaultListVO.addEAFDefaultVOToList(eafDefaultVO);
        }
        return new ResponseEntity<>(eafDefaultListVO, HttpStatus.OK);
    }

    /**
     * Get eaf early design default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    @RequestMapping(value="/eaf_early_design", method = RequestMethod.GET)
    public ResponseEntity getDefaultEafEarlyDesign() {
        List<EAFEarlyDesignDefault> eafEarlyDesignDefaults = eafService.getEafEarlyDesignDefault();
        EAFEarlyDesignDefaultListVO eafEarlyDesignDefaultListVO = new EAFEarlyDesignDefaultListVO();
        for (EAFEarlyDesignDefault eafEarlyDesignDefault : eafEarlyDesignDefaults){
            EAFEarlyDesignDefaultVO eafEarlyDesignDefaultVO = new EAFEarlyDesignDefaultVO();
            BeanUtils.copyProperties(eafEarlyDesignDefault,eafEarlyDesignDefaultVO);
            eafEarlyDesignDefaultListVO.addEAFEarlyDesignDefaultVOToList(eafEarlyDesignDefaultVO);
        }
        return new ResponseEntity<>(eafEarlyDesignDefaultListVO, HttpStatus.OK);
    }

    /**
     * Get equation factor default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    @RequestMapping(value="/equation", method = RequestMethod.GET)
    public ResponseEntity getDefaultEquation() {
        List<EquationFactorDefault> equationFactorDefaults = equationService.getEquationFactorDefault();
        EquationFactorDefaultListVO equationFactorDefaultListVO = new EquationFactorDefaultListVO();
        for (EquationFactorDefault equationFactorDefault : equationFactorDefaults){
            EquationFactorDefaultVO equationFactorDefaultVO = new EquationFactorDefaultVO();
            BeanUtils.copyProperties(equationFactorDefault,equationFactorDefaultVO);
            equationFactorDefaultListVO.addEquationFactorDefaultVOToList(equationFactorDefaultVO);
        }
        return new ResponseEntity<>(equationFactorDefaultListVO, HttpStatus.OK);
    }

    /**
     * Get eaf maintenance default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    @RequestMapping(value="/eaf_maintenance", method = RequestMethod.GET)
    public ResponseEntity getDefaultEafMaintenance() {
        List<EAFMaintenanceDefault> eafMaintenanceDefaults = eafService.getEafMaintenanceDefault();
        EAFMaintenanceDefaultListVO eafMaintenanceDefaultListVO = new EAFMaintenanceDefaultListVO();
        for (EAFMaintenanceDefault eafMaintenanceDefault : eafMaintenanceDefaults){
            EAFMaintenanceDefaultVO eafMaintenanceDefaultVO = new EAFMaintenanceDefaultVO();
            BeanUtils.copyProperties(eafMaintenanceDefault,eafMaintenanceDefaultVO);
            eafMaintenanceDefaultListVO.addEAFMaintenanceDefaultVOToList(eafMaintenanceDefaultVO);
        }
        return new ResponseEntity<>(eafMaintenanceDefaultListVO, HttpStatus.OK);
    }
}
