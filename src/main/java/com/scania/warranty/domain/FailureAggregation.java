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
 * Value object for aggregating claim position data by failure number.
 */
public class FailureAggregation {
    private int failureNumber;
    private boolean isMaintenance;
    private String groups;
    private String partNumber;
    private BigDecimal partValue;
    private List<String> textLines;
    private BigDecimal valueMaterial;
    private BigDecimal valueLabor;
    private BigDecimal valueSpecial;

    public FailureAggregation() {
        this.textLines = new ArrayList<>();
        this.valueMaterial = BigDecimal.ZERO;
        this.valueLabor = BigDecimal.ZERO;
        this.valueSpecial = BigDecimal.ZERO;
        this.partValue = BigDecimal.ZERO;
        this.groups = "";
        this.partNumber = "";
    }

    public void reset(int newFailureNumber) {
        this.failureNumber = newFailureNumber;
        this.isMaintenance = false;
        this.groups = "";
        this.partNumber = "";
        this.partValue = BigDecimal.ZERO;
        this.textLines.clear();
        this.valueMaterial = BigDecimal.ZERO;
        this.valueLabor = BigDecimal.ZERO;
        this.valueSpecial = BigDecimal.ZERO;
    }

    public void addTextLine(String text) {
        if (textLines.size() < 4 && text != null && !text.startsWith("+")) {
            textLines.add(text);
        }
    }

    public void addMaterialValue(BigDecimal quantity, BigDecimal value) {
        this.valueMaterial = this.valueMaterial.add(quantity.multiply(value));
    }

    public void addLaborValue(BigDecimal quantity, BigDecimal value) {
        this.valueLabor = this.valueLabor.add(quantity.multiply(value));
    }

    public void addSpecialValue(BigDecimal value) {
        this.valueSpecial = this.valueSpecial.add(value);
    }

    public void updatePartIfHigherValue(String partNo, BigDecimal value) {
        if (value.compareTo(this.partValue) > 0) {
            this.partNumber = partNo;
            this.partValue = value;
        }
    }

    // Getters and Setters
    public int getFailureNumber() {
        return failureNumber;
    }

    public void setFailureNumber(int failureNumber) {
        this.failureNumber = failureNumber;
    }

    public boolean isMaintenance() {
        return isMaintenance;
    }

    public void setMaintenance(boolean maintenance) {
        isMaintenance = maintenance;
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

    public BigDecimal getPartValue() {
        return partValue;
    }

    public void setPartValue(BigDecimal partValue) {
        this.partValue = partValue;
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