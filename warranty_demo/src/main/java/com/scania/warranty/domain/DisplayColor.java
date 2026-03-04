/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n404}.
 */

package com.scania.warranty.domain;

/**
 * Enum for claim display colors (red/yellow/blue).
 */
public enum DisplayColor {
    RED("ROT"),
    YELLOW("GELB"),
    BLUE("BLAU"),
    NONE("");

    private final String code;

    DisplayColor(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}