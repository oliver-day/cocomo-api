package com.cocomo.service.Impl;

import com.cocomo.bo.DefaultBO;
import com.cocomo.bo.EAFEarlyDesignBO;
import com.cocomo.bo.EafBO;
import com.cocomo.common.Const;
import com.cocomo.dao.EAFDefaultMapper;
import com.cocomo.dao.EAFEarlyDesignDefaultMapper;
import com.cocomo.dao.EAFMaintenanceDefaultMapper;
import com.cocomo.pojo.EAFDefault;
import com.cocomo.pojo.EAFEarlyDesignDefault;
import com.cocomo.pojo.EAFMaintenanceDefault;
import com.cocomo.pojo.SDefault;
import com.cocomo.service.EAFService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * The implementation of
 * EAF service
 * @author Haoming Zhang
 * @date 3/4/19 19:37
 */
@Service("eafService")
public class EAFServiceImpl implements EAFService {

    @Autowired
    private EAFDefaultMapper eafDefaultMapper;

    @Autowired
    private EAFEarlyDesignDefaultMapper eafEarlyDesignDefaultMapper;

    @Autowired
    private EAFMaintenanceDefaultMapper eafMaintenanceDefaultMapper;

    /**
     * Check whether a eaf
     * driver name exists or not.
     * @author Haoming Zhang
     * @date 3/4/19 19:37
     */
    @Override
    public int checkDriverName(String driverName) {
        return eafDefaultMapper.checkDriverName(driverName);
    }

    /**
     * Check whether a eaf_earlydesign
     * driver name exists or not.
     * @author Haoming Zhang
     * @date 3/8/19 19:32
     */
    @Override
    public int checkDriverNameForEarlyDesign(String driverName) {
        return eafEarlyDesignDefaultMapper.checkDriverName(driverName);
    }

    /**
     * Check whether a eaf_maintenance
     * driver name exists or not.
     * @author Haoming Zhang
     * @date 3/13/19 14:41
     */
    @Override
    public int checkDriverNameForMaintenance(String driverName) {
        return eafMaintenanceDefaultMapper.checkDriverName(driverName);
    }

    /**
     * Update eaf defaults.
     * @author Haoming Zhang
     * @date 3/4/19 19:47
     */
    @Override
    public Boolean updateEAFDefault(DefaultBO defaultBO) {
        int rowCount = eafDefaultMapper.updateByUserInput(defaultBO.getDriverName(),
                Const.DriverLevel.getValue(defaultBO.getDriverLevel()),
                defaultBO.getValue());

        if (rowCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * Update schedule defaults
     * @author Haoming Zhang
     * @date 3/29/19 22:26
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Boolean updateScheduleDefault(DefaultBO defaultBO) {
        int eafEarlyrowCount = eafEarlyDesignDefaultMapper.updateByUserInput(defaultBO.getDriverName(),
                Const.DriverLevel.getValue(defaultBO.getDriverLevel()),
                defaultBO.getValue());

        int eafRowCount = eafDefaultMapper.updateByUserInput(defaultBO.getDriverName(),
                Const.DriverLevel.getValue(defaultBO.getDriverLevel()),
                defaultBO.getValue());

        if (eafRowCount > 0 && eafEarlyrowCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * Update eaf_earlydesign defaults
     * @author Haoming Zhang
     * @date 3/8/19 19:24
     */
    @Override
    public Boolean updateEAFEarlyDesignDefault(DefaultBO defaultBO) {
        int rowCount = eafEarlyDesignDefaultMapper.updateByUserInput(defaultBO.getDriverName(),
                Const.EAFEarlyDesignDriverLevel.getValue(defaultBO.getDriverLevel()),
                defaultBO.getValue());

        if (rowCount > 0) {
            return true;
        }

        return false;
    }

    /**
     * Update eaf_maintenance defaults
     * @author Haoming Zhang
     * @date 3/13/19 15:09
     */
    @Override
    public Boolean updateMaintenanceDefault(DefaultBO defaultBO) {
        int count = eafMaintenanceDefaultMapper.updateByUserInput(defaultBO.getDriverName(),
                Const.EAFDriverLevel.getValue(defaultBO.getDriverLevel()),
                defaultBO.getValue());

        return count > 0;
    }

    /**
     * Calculate eaf for early
     * design project
     * @author Haoming Zhang
     * @date 3/9/19 13:35
     */
    @Override
    public Double calculateEarlyDesignEAF(EAFEarlyDesignBO eafEarlyDesignBO) {
        List<Map<String, Object>> eafDefaults = eafEarlyDesignDefaultMapper.selectAllDefault();
        Double result = 0.0;

        // Sorting
        eafDefaults.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
            Integer id2 = Integer.valueOf(o2.get("id").toString()) ;
            Integer id1 = Integer.valueOf(o1.get("id").toString()) ;
            return id1.compareTo(id2);
        });


        Integer pers = eafEarlyDesignBO.getPers();
        String persLevel = Const.EAFEarlyDesignDriverLevel.getValue(pers);
        Double persValue = (Double) eafDefaults.get(1).get(persLevel);

        Integer rcpx = eafEarlyDesignBO.getRcpx();
        String rcpxLevel = Const.EAFEarlyDesignDriverLevel.getValue(rcpx);
        Double rcpxValue = (Double) eafDefaults.get(2).get(rcpxLevel);

        Integer pdif = eafEarlyDesignBO.getPdif();
        String pdifLevel = Const.EAFEarlyDesignDriverLevel.getValue(pdif);
        Double pdifValue = (Double) eafDefaults.get(3).get(pdifLevel);

        Integer prex = eafEarlyDesignBO.getPrex();
        String prexLevel = Const.EAFEarlyDesignDriverLevel.getValue(prex);
        Double prexValue = (Double) eafDefaults.get(4).get(prexLevel);

        Integer fcil = eafEarlyDesignBO.getFcil();
        String fcilLevel = Const.EAFEarlyDesignDriverLevel.getValue(fcil);
        Double fcilValue = (Double) eafDefaults.get(5).get(fcilLevel);

        Integer ruse = eafEarlyDesignBO.getRuse();
        String ruseLevel = Const.EAFEarlyDesignDriverLevel.getValue(ruse);
        Double ruseValue = (Double) eafDefaults.get(6).get(ruseLevel);

        result = persValue * rcpxValue * pdifValue * prexValue * fcilValue * ruseValue;

        return result;
    }

    /**
     * Calculate eaf for post-
     * architecture projects
     * @author Haoming Zhang
     * @date 3/9/19 19:44
     */
    @Override
    public Double calculateEAF(EafBO eafBO) {
        List<Map<String, Object>> eafDefaults = eafDefaultMapper.selectAllDefault();
        Double result = 0.0;

        // Sorting
        eafDefaults.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
            Integer id2 = Integer.valueOf(o2.get("id").toString()) ;
            Integer id1 = Integer.valueOf(o1.get("id").toString()) ;
            return id1.compareTo(id2);
        });

        Integer rely = eafBO.getRely();
        String relyLevel = Const.EAFDriverLevel.getValue(rely);
        Double relyValue = (Double) eafDefaults.get(1).get(relyLevel);

        Integer data = eafBO.getData();
        String dataLevel = Const.EAFDriverLevel.getValue(data);
        Double dataValue = (Double) eafDefaults.get(2).get(dataLevel);

        Integer docu = eafBO.getDocu();
        String docuLevel = Const.EAFDriverLevel.getValue(docu);
        Double docuValue = (Double) eafDefaults.get(3).get(docuLevel);

        Integer cplx = eafBO.getCplx();
        String cplxLevel = Const.EAFDriverLevel.getValue(cplx);
        Double cplxValue = (Double) eafDefaults.get(4).get(cplxLevel);

        Integer ruse = eafBO.getRuse();
        String ruseLevel = Const.EAFDriverLevel.getValue(ruse);
        Double ruseValue = (Double) eafDefaults.get(5).get(ruseLevel);

        Integer time = eafBO.getTime();
        String timeLevel = Const.EAFDriverLevel.getValue(time);
        Double timeValue = (Double) eafDefaults.get(6).get(timeLevel);

        Integer stor = eafBO.getStor();
        String storLevel = Const.EAFDriverLevel.getValue(stor);
        Double storValue = (Double) eafDefaults.get(7).get(storLevel);

        Integer pvol = eafBO.getPvol();
        String pvolLevel = Const.EAFDriverLevel.getValue(pvol);
        Double pvolValue = (Double) eafDefaults.get(8).get(pvolLevel);

        Integer acap = eafBO.getAcap();
        String acapLevel = Const.EAFDriverLevel.getValue(acap);
        Double acapValue = (Double) eafDefaults.get(9).get(acapLevel);

        Integer pcap = eafBO.getPcap();
        String pcapLevel = Const.EAFDriverLevel.getValue(pcap);
        Double pcapValue = (Double) eafDefaults.get(10).get(pcapLevel);

        Integer pcon = eafBO.getPcon();
        String pconLevel = Const.EAFDriverLevel.getValue(pcon);
        Double pconValue = (Double) eafDefaults.get(11).get(pconLevel);

        Integer apex = eafBO.getApex();
        String apexLevel = Const.EAFDriverLevel.getValue(apex);
        Double apexValue = (Double) eafDefaults.get(12).get(apexLevel);

        Integer ltex = eafBO.getLtex();
        String ltexLevel = Const.EAFDriverLevel.getValue(ltex);
        Double ltexValue = (Double) eafDefaults.get(13).get(ltexLevel);

        Integer plex = eafBO.getPlex();
        String plexLevel = Const.EAFDriverLevel.getValue(plex);
        Double plexValue = (Double) eafDefaults.get(14).get(plexLevel);

        Integer tool = eafBO.getTool();
        String toolLevel = Const.EAFDriverLevel.getValue(tool);
        Double toolValue = (Double) eafDefaults.get(15).get(toolLevel);

        Integer site = eafBO.getSite();
        String siteLevel = Const.EAFDriverLevel.getValue(site);
        Double siteValue = (Double) eafDefaults.get(16).get(siteLevel);

        return relyValue * dataValue * docuValue * cplxValue * ruseValue
                * timeValue * storValue * pvolValue * acapValue * pcapValue
                * pconValue * apexValue * ltexValue * plexValue * toolValue
                * siteValue;
    }

    /**
     * Get eaf default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    @Override
    public List<EAFDefault> getEafDefault(){ return eafDefaultMapper.getEafDefault(); }

    /**
     * Get eaf early design default values
     * @author Dongchul Choi
     * @date 2019-03-24
     */
    @Override
    public List<EAFEarlyDesignDefault> getEafEarlyDesignDefault(){ return eafEarlyDesignDefaultMapper.getEafEarlyDesignDefault(); }

    /**
     * Calculate eaf for maintenance
     * @author Haoming Zhang
     * @date 4/5/19 14:32
     */
    @Override
    public Double calculateMaintenanceEAF(EafBO eafBO) {
        List<Map<String, Object>> eafDefaults = eafMaintenanceDefaultMapper.selectAllDefault();
        Double result = 0.0;

        // Sorting
        eafDefaults.sort((Map<String, Object> o1, Map<String, Object> o2) -> {
            Integer id2 = Integer.valueOf(o2.get("id").toString()) ;
            Integer id1 = Integer.valueOf(o1.get("id").toString()) ;
            return id1.compareTo(id2);
        });

        Integer rely = eafBO.getRely();
        String relyLevel = Const.EAFDriverLevel.getValue(rely);
        Double relyValue = (Double) eafDefaults.get(0).get(relyLevel);

        Integer data = eafBO.getData();
        String dataLevel = Const.EAFDriverLevel.getValue(data);
        Double dataValue = (Double) eafDefaults.get(1).get(dataLevel);

        Integer docu = eafBO.getDocu();
        String docuLevel = Const.EAFDriverLevel.getValue(docu);
        Double docuValue = (Double) eafDefaults.get(2).get(docuLevel);

        Integer cplx = eafBO.getCplx();
        String cplxLevel = Const.EAFDriverLevel.getValue(cplx);
        Double cplxValue = (Double) eafDefaults.get(3).get(cplxLevel);

        Integer ruse = eafBO.getRuse();
        String ruseLevel = Const.EAFDriverLevel.getValue(ruse);
        Double ruseValue = (Double) eafDefaults.get(4).get(ruseLevel);

        Integer time = eafBO.getTime();
        String timeLevel = Const.EAFDriverLevel.getValue(time);
        Double timeValue = (Double) eafDefaults.get(5).get(timeLevel);

        Integer stor = eafBO.getStor();
        String storLevel = Const.EAFDriverLevel.getValue(stor);
        Double storValue = (Double) eafDefaults.get(6).get(storLevel);

        Integer pvol = eafBO.getPvol();
        String pvolLevel = Const.EAFDriverLevel.getValue(pvol);
        Double pvolValue = (Double) eafDefaults.get(7).get(pvolLevel);

        Integer acap = eafBO.getAcap();
        String acapLevel = Const.EAFDriverLevel.getValue(acap);
        Double acapValue = (Double) eafDefaults.get(8).get(acapLevel);

        Integer pcap = eafBO.getPcap();
        String pcapLevel = Const.EAFDriverLevel.getValue(pcap);
        Double pcapValue = (Double) eafDefaults.get(9).get(pcapLevel);

        Integer pcon = eafBO.getPcon();
        String pconLevel = Const.EAFDriverLevel.getValue(pcon);
        Double pconValue = (Double) eafDefaults.get(10).get(pconLevel);

        Integer apex = eafBO.getApex();
        String apexLevel = Const.EAFDriverLevel.getValue(apex);
        Double apexValue = (Double) eafDefaults.get(11).get(apexLevel);

        Integer ltex = eafBO.getLtex();
        String ltexLevel = Const.EAFDriverLevel.getValue(ltex);
        Double ltexValue = (Double) eafDefaults.get(12).get(ltexLevel);

        Integer plex = eafBO.getPlex();
        String plexLevel = Const.EAFDriverLevel.getValue(plex);
        Double plexValue = (Double) eafDefaults.get(13).get(plexLevel);

        Integer tool = eafBO.getTool();
        String toolLevel = Const.EAFDriverLevel.getValue(tool);
        Double toolValue = (Double) eafDefaults.get(14).get(toolLevel);

        Integer site = eafBO.getSite();
        String siteLevel = Const.EAFDriverLevel.getValue(site);
        Double siteValue = (Double) eafDefaults.get(15).get(siteLevel);

        return relyValue * dataValue * docuValue * cplxValue * ruseValue
                * timeValue * storValue * pvolValue * acapValue * pcapValue
                * pconValue * apexValue * ltexValue * plexValue * toolValue
                * siteValue;
    }

    /**
     * Get eaf maintenance default values
     * @author Dongchul Choi
     * @date 2019-04-22
     */
    public List<EAFMaintenanceDefault> getEafMaintenanceDefault(){ return eafMaintenanceDefaultMapper.getEafMaintenanceDefault(); }
}
