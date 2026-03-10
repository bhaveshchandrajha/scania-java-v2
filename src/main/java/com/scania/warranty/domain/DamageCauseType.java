/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1919}.
 */

package com.scania.warranty.domain;

/**
 * Enum for damage cause types (customer coding field G73360).
 */
public enum DamageCauseType {
    CAMPAIGN("3"),
    OTHER_CUSTOMER_CAUSE(""),
    WORKSHOP_CAUSE_97("97");

    private final String code;

    DamageCauseType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static DamageCauseType fromCode(String code) {
        if (code == null) {
            return OTHER_CUSTOMER_CAUSE;
        }
        String trimmed = code.trim();
        for (DamageCauseType type : values()) {
            if (type.code.equals(trimmed)) {
                return type;
            }
        }
        return OTHER_CUSTOMER_CAUSE;
    }
}