/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Value object representing aggregated failure data during claim creation.
 */
public class FailureData {
    private Integer failureNumber;
    private String groups;
    private String partNumber;
    private boolean maintenance;
    private List<String> textLines;
    private BigDecimal valueMaterial;
    private BigDecimal valueLabor;
    private BigDecimal valueSpecial;

    public FailureData() {
        this.textLines = new ArrayList<>();
        this.valueMaterial = BigDecimal.ZERO;
        this.valueLabor = BigDecimal.ZERO;
        this.valueSpecial = BigDecimal.ZERO;
    }

    public Integer getFailureNumber() {
        return failureNumber;
    }

    public void setFailureNumber(Integer failureNumber) {
        this.failureNumber = failureNumber;
    }

    public String getGroups() {
        return groups;
    }

    public void setGroups(String groups) {
        this.groups = groups;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public boolean isMaintenance() {
        return maintenance;
    }

    public void setMaintenance(boolean maintenance) {
        this.maintenance = maintenance;
    }

    public List<String> getTextLines() {
        return textLines;
    }

    public void setTextLines(List<String> textLines) {
        this.textLines = textLines;
    }

    public BigDecimal getValueMaterial() {
        return valueMaterial;
    }

    public void setValueMaterial(BigDecimal valueMaterial) {
        this.valueMaterial = valueMaterial;
    }

    public BigDecimal getValueLabor() {
        return valueLabor;
    }

    public void setValueLabor(BigDecimal valueLabor) {
        this.valueLabor = valueLabor;
    }

    public BigDecimal getValueSpecial() {
        return valueSpecial;
    }

    public void setValueSpecial(BigDecimal valueSpecial) {
        this.valueSpecial = valueSpecial;
    }
}