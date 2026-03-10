/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ClaimCreationContext {
    
    private boolean createFlag; // @rpg-trace: n1802
    private boolean maintenanceFlag; // @rpg-trace: n1791
    private String groups; // @rpg-trace: n1796
    private String partNo; // @rpg-trace: n1797
    private BigDecimal partValue; // @rpg-trace: n1798
    private List<String> textLines; // @rpg-trace: n1792
    private int currentFailureNo; // @rpg-trace: n1790
    private int lineNo; // @rpg-trace: n1792
    private BigDecimal valueMat; // @rpg-trace: n1793
    private BigDecimal valueLab; // @rpg-trace: n1794
    private BigDecimal valueSpe; // @rpg-trace: n1795

    public ClaimCreationContext() {
        this.createFlag = false; // @rpg-trace: n1802
        this.maintenanceFlag = false; // @rpg-trace: n1791
        this.groups = ""; // @rpg-trace: n1796
        this.partNo = ""; // @rpg-trace: n1797
        this.partValue = BigDecimal.ZERO; // @rpg-trace: n1798
        this.textLines = new ArrayList<>(); // @rpg-trace: n1792
        this.currentFailureNo = 1; // @rpg-trace: n1783
        this.lineNo = 0; // @rpg-trace: n1792
        this.valueMat = BigDecimal.ZERO; // @rpg-trace: n1793
        this.valueLab = BigDecimal.ZERO; // @rpg-trace: n1794
        this.valueSpe = BigDecimal.ZERO; // @rpg-trace: n1795
    }

    public void resetForNewFailure(int newFailureNo) {
        this.currentFailureNo = newFailureNo; // @rpg-trace: n1790
        this.maintenanceFlag = false; // @rpg-trace: n1791
        this.textLines.clear(); // @rpg-trace: n1792
        this.lineNo = 0; // @rpg-trace: n1792
        this.valueMat = BigDecimal.ZERO; // @rpg-trace: n1793
        this.valueLab = BigDecimal.ZERO; // @rpg-trace: n1794
        this.valueSpe = BigDecimal.ZERO; // @rpg-trace: n1795
        this.groups = ""; // @rpg-trace: n1796
        this.partNo = ""; // @rpg-trace: n1797
        this.partValue = BigDecimal.ZERO; // @rpg-trace: n1798
    }

    public boolean isCreateFlag() {
        return createFlag;
    }

    public void setCreateFlag(boolean createFlag) {
        this.createFlag = createFlag;
    }

    public boolean isMaintenanceFlag() {
        return maintenanceFlag;
    }

    public void setMaintenanceFlag(boolean maintenanceFlag) {
        this.maintenanceFlag = maintenanceFlag;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getPartNo() {
        return partNo;
    }

    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }

    public BigDecimal getPartValue() {
        return partValue;
    }

    public void setPartValue(BigDecimal partValue) {
        this.partValue = partValue;
    }

    public List<String> getTextLines() {
        return textLines;
    }

    public void addTextLine(String text) {
        if (this.lineNo < 4) { // @rpg-trace: n1792
            this.textLines.add(text); // @rpg-trace: n1792
            this.lineNo++; // @rpg-trace: n1792
        }
    }

    public int getCurrentFailureNo() {
        return currentFailureNo;
    }

    public void setCurrentFailureNo(int currentFailureNo) {
        this.currentFailureNo = currentFailureNo;
    }

    public int getLineNo() {
        return lineNo;
    }

    public void setLineNo(int lineNo) {
        this.lineNo = lineNo;
    }

    public BigDecimal getValueMat() {
        return valueMat;
    }

    public void setValueMat(BigDecimal valueMat) {
        this.valueMat = valueMat;
    }

    public BigDecimal getValueLab() {
        return valueLab;
    }

    public void setValueLab(BigDecimal valueLab) {
        this.valueLab = valueLab;
    }

    public BigDecimal getValueSpe() {
        return valueSpe;
    }

    public void setValueSpe(BigDecimal valueSpe) {
        this.valueSpe = valueSpe;
    }
}