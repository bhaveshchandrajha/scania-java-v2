/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

/**
 * Enum for claim position record types (SATZART).
 */
public enum PositionType {
    MAT("MAT", "Material"),
    ARB("ARB", "Labor"),
    TXT("TXT", "Text"),
    OTHER("OTHER", "Special/Other");

    private final String code;
    private final String description;

    PositionType(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static PositionType fromCode(String code) {
        for (PositionType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return OTHER;
    }
}