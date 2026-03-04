/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1919}.
 */

package com.scania.warranty.domain;

/**
 * Enum for special cost record types (SATZART field GPS040).
 */
public enum SpecialCostType {
    SPECIAL_COST("SMA"),
    OTHER("");

    private final String code;

    SpecialCostType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static SpecialCostType fromCode(String code) {
        if (code == null) {
            return OTHER;
        }
        for (SpecialCostType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return OTHER;
    }
}