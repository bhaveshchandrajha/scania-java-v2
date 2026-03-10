/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

/**
 * Enum for claim position record types (SATZART).
 */
public enum RecordType {
    MAT("MAT", "Material"),
    ARB("ARB", "Labor"),
    TXT("TXT", "Text"),
    SPE("SPE", "Special");
    
    private final String code;
    private final String description;
    
    RecordType(String code, String description) {
        this.code = code;
        this.description = description;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getDescription() {
        return description;
    }
    
    public static RecordType fromCode(String code) {
        for (RecordType type : values()) {
            if (type.code.equals(code)) {
                return type;
            }
        }
        return null;
    }
}