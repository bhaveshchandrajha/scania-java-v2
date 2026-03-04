/**
 * Domain entity or value object for the warranty claims model.
 * <p>
 * Generated from RPG: unit {@code HS1210}, node {@code n1779}.
 */

package com.scania.warranty.domain;

/**
 * Enum for claim header status codes (STATUS CODE SDE).
 */
public enum ClaimHeaderStatus {
    CREATED(3, "Created");

    private final int code;
    private final String description;

    ClaimHeaderStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}